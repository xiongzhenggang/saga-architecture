package com.xzg.orchestrator.kit.message.consumer;

import com.xzg.orchestrator.kit.message.consumer.model.MetaAttribute;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: MultiMessageDecoder
 * @author: xzg
 * @description: TODO
 * @date: 25/11/2023-上午 10:50
 * @version: 1.0
 */
public class MultiMessageDecoder
{
    public static final int BLOCK_LENGTH = 0;
    public static final int TEMPLATE_ID = 1;
    public static final int SCHEMA_ID = 1;
    public static final int SCHEMA_VERSION = 0;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private final MultiMessageDecoder parentMessage = this;
    private DirectBuffer buffer;
    protected int offset;
    protected int limit;
    protected int actingBlockLength;
    protected int actingVersion;

    public int sbeBlockLength()
    {
        return BLOCK_LENGTH;
    }

    public int sbeTemplateId()
    {
        return TEMPLATE_ID;
    }

    public int sbeSchemaId()
    {
        return SCHEMA_ID;
    }

    public int sbeSchemaVersion()
    {
        return SCHEMA_VERSION;
    }

    public String sbeSemanticType()
    {
        return "";
    }

    public DirectBuffer buffer()
    {
        return buffer;
    }

    public int offset()
    {
        return offset;
    }

    public MultiMessageDecoder wrap(
            final DirectBuffer buffer,
            final int offset,
            final int actingBlockLength,
            final int actingVersion)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.offset = offset;
        this.actingBlockLength = actingBlockLength;
        this.actingVersion = actingVersion;
        limit(offset + actingBlockLength);

        return this;
    }

    public int encodedLength()
    {
        return limit - offset;
    }

    public int limit()
    {
        return limit;
    }

    public void limit(final int limit)
    {
        this.limit = limit;
    }

    private final HeadersDecoder headers = new HeadersDecoder(this);

    public static long headersDecoderId()
    {
        return 13;
    }

    public static int headersDecoderSinceVersion()
    {
        return 0;
    }

    public HeadersDecoder headers()
    {
        headers.wrap(buffer);
        return headers;
    }

    public static class HeadersDecoder
            implements Iterable<HeadersDecoder>, java.util.Iterator<HeadersDecoder>
    {
        public static final int HEADER_SIZE = 4;
        private final MultiMessageDecoder parentMessage;
        private DirectBuffer buffer;
        private int count;
        private int index;
        private int offset;
        private int blockLength;

        HeadersDecoder(final MultiMessageDecoder parentMessage)
        {
            this.parentMessage = parentMessage;
        }

        public void wrap(final DirectBuffer buffer)
        {
            if (buffer != this.buffer)
            {
                this.buffer = buffer;
            }
            index = -1;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + HEADER_SIZE);
            blockLength = (int)(buffer.getShort(limit + 0, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
            count = (int)(buffer.getShort(limit + 2, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
        }

        public static int sbeHeaderSize()
        {
            return HEADER_SIZE;
        }

        public static int sbeBlockLength()
        {
            return 0;
        }

        public int actingBlockLength()
        {
            return blockLength;
        }

        public int count()
        {
            return count;
        }

        public java.util.Iterator<HeadersDecoder> iterator()
        {
            return this;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext()
        {
            return (index + 1) < count;
        }

        public HeadersDecoder next()
        {
            if (index + 1 >= count)
            {
                throw new java.util.NoSuchElementException();
            }

            offset = parentMessage.limit();
            parentMessage.limit(offset + blockLength);
            ++index;

            return this;
        }

        public static int keyId()
        {
            return 17;
        }

        public static int keySinceVersion()
        {
            return 0;
        }

        public static String keyCharacterEncoding()
        {
            return "UTF-8";
        }

        public static String keyMetaAttribute(final MetaAttribute metaAttribute)
        {
            switch (metaAttribute)
            {
                case EPOCH: return "unix";
                case TIME_UNIT: return "nanosecond";
                case SEMANTIC_TYPE: return "";
                case PRESENCE: return "required";
            }

            return "";
        }

        public static int keyHeaderLength()
        {
            return 4;
        }

        public int keyLength()
        {
            final int limit = parentMessage.limit();
            return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        }

        public int skipKey()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int dataOffset = limit + headerLength;

            parentMessage.limit(dataOffset + dataLength);

            return dataLength;
        }

        public int getKey(final MutableDirectBuffer dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public int getKey(final byte[] dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public void wrapKey(final DirectBuffer wrapBuffer)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);
            wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
        }

        public String key()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);

            if (0 == dataLength)
            {
                return "";
            }

            final byte[] tmp = new byte[dataLength];
            buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

            final String value;
            try
            {
                value = new String(tmp, "UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            return value;
        }

        public static int valueId()
        {
            return 18;
        }

        public static int valueSinceVersion()
        {
            return 0;
        }

        public static String valueCharacterEncoding()
        {
            return "UTF-8";
        }

        public static String valueMetaAttribute(final MetaAttribute metaAttribute)
        {
            switch (metaAttribute)
            {
                case EPOCH: return "unix";
                case TIME_UNIT: return "nanosecond";
                case SEMANTIC_TYPE: return "";
                case PRESENCE: return "required";
            }

            return "";
        }

        public static int valueHeaderLength()
        {
            return 4;
        }

        public int valueLength()
        {
            final int limit = parentMessage.limit();
            return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        }

        public int skipValue()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int dataOffset = limit + headerLength;

            parentMessage.limit(dataOffset + dataLength);

            return dataLength;
        }

        public int getValue(final MutableDirectBuffer dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public int getValue(final byte[] dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public void wrapValue(final DirectBuffer wrapBuffer)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);
            wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
        }

        public String value()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);

            if (0 == dataLength)
            {
                return "";
            }

            final byte[] tmp = new byte[dataLength];
            buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

            final String value;
            try
            {
                value = new String(tmp, "UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            return value;
        }


        public String toString()
        {
            return appendTo(new StringBuilder(100)).toString();
        }

        public StringBuilder appendTo(final StringBuilder builder)
        {
            builder.append('(');
            builder.append("key=");
            builder.append('\'').append(key()).append('\'');
            builder.append('|');
            builder.append("value=");
            builder.append('\'').append(value()).append('\'');
            builder.append(')');
            return builder;
        }
    }

    private final MessagesDecoder messages = new MessagesDecoder(this);

    public static long messagesDecoderId()
    {
        return 12;
    }

    public static int messagesDecoderSinceVersion()
    {
        return 0;
    }

    public MessagesDecoder messages()
    {
        messages.wrap(buffer);
        return messages;
    }

    public static class MessagesDecoder
            implements Iterable<MessagesDecoder>, java.util.Iterator<MessagesDecoder>
    {
        public static final int HEADER_SIZE = 4;
        private final MultiMessageDecoder parentMessage;
        private DirectBuffer buffer;
        private int count;
        private int index;
        private int offset;
        private int blockLength;
        private final HeadersDecoder headers;

        MessagesDecoder(final MultiMessageDecoder parentMessage)
        {
            this.parentMessage = parentMessage;
            headers = new HeadersDecoder(parentMessage);
        }

        public void wrap(final DirectBuffer buffer)
        {
            if (buffer != this.buffer)
            {
                this.buffer = buffer;
            }
            index = -1;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + HEADER_SIZE);
            blockLength = (int)(buffer.getShort(limit + 0, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
            count = (int)(buffer.getShort(limit + 2, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
        }

        public static int sbeHeaderSize()
        {
            return HEADER_SIZE;
        }

        public static int sbeBlockLength()
        {
            return 0;
        }

        public int actingBlockLength()
        {
            return blockLength;
        }

        public int count()
        {
            return count;
        }

        public java.util.Iterator<MessagesDecoder> iterator()
        {
            return this;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext()
        {
            return (index + 1) < count;
        }

        public MessagesDecoder next()
        {
            if (index + 1 >= count)
            {
                throw new java.util.NoSuchElementException();
            }

            offset = parentMessage.limit();
            parentMessage.limit(offset + blockLength);
            ++index;

            return this;
        }

        public static long headersDecoderId()
        {
            return 14;
        }

        public static int headersDecoderSinceVersion()
        {
            return 0;
        }

        public HeadersDecoder headers()
        {
            headers.wrap(buffer);
            return headers;
        }

        public static class HeadersDecoder
                implements Iterable<HeadersDecoder>, java.util.Iterator<HeadersDecoder>
        {
            public static final int HEADER_SIZE = 4;
            private final MultiMessageDecoder parentMessage;
            private DirectBuffer buffer;
            private int count;
            private int index;
            private int offset;
            private int blockLength;

            HeadersDecoder(final MultiMessageDecoder parentMessage)
            {
                this.parentMessage = parentMessage;
            }

            public void wrap(final DirectBuffer buffer)
            {
                if (buffer != this.buffer)
                {
                    this.buffer = buffer;
                }
                index = -1;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + HEADER_SIZE);
                blockLength = (int)(buffer.getShort(limit + 0, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
                count = (int)(buffer.getShort(limit + 2, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
            }

            public static int sbeHeaderSize()
            {
                return HEADER_SIZE;
            }

            public static int sbeBlockLength()
            {
                return 0;
            }

            public int actingBlockLength()
            {
                return blockLength;
            }

            public int count()
            {
                return count;
            }

            public java.util.Iterator<HeadersDecoder> iterator()
            {
                return this;
            }

            public void remove()
            {
                throw new UnsupportedOperationException();
            }

            public boolean hasNext()
            {
                return (index + 1) < count;
            }

            public HeadersDecoder next()
            {
                if (index + 1 >= count)
                {
                    throw new java.util.NoSuchElementException();
                }

                offset = parentMessage.limit();
                parentMessage.limit(offset + blockLength);
                ++index;

                return this;
            }

            public static int keyId()
            {
                return 19;
            }

            public static int keySinceVersion()
            {
                return 0;
            }

            public static String keyCharacterEncoding()
            {
                return "UTF-8";
            }

            public static String keyMetaAttribute(final MetaAttribute metaAttribute)
            {
                switch (metaAttribute)
                {
                    case EPOCH: return "unix";
                    case TIME_UNIT: return "nanosecond";
                    case SEMANTIC_TYPE: return "";
                    case PRESENCE: return "required";
                }

                return "";
            }

            public static int keyHeaderLength()
            {
                return 4;
            }

            public int keyLength()
            {
                final int limit = parentMessage.limit();
                return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            }

            public int skipKey()
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                final int dataOffset = limit + headerLength;

                parentMessage.limit(dataOffset + dataLength);

                return dataLength;
            }

            public int getKey(final MutableDirectBuffer dst, final int dstOffset, final int length)
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                final int bytesCopied = Math.min(length, dataLength);
                parentMessage.limit(limit + headerLength + dataLength);
                buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

                return bytesCopied;
            }

            public int getKey(final byte[] dst, final int dstOffset, final int length)
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                final int bytesCopied = Math.min(length, dataLength);
                parentMessage.limit(limit + headerLength + dataLength);
                buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

                return bytesCopied;
            }

            public void wrapKey(final DirectBuffer wrapBuffer)
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                parentMessage.limit(limit + headerLength + dataLength);
                wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
            }

            public String key()
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                parentMessage.limit(limit + headerLength + dataLength);

                if (0 == dataLength)
                {
                    return "";
                }

                final byte[] tmp = new byte[dataLength];
                buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

                final String value;
                try
                {
                    value = new String(tmp, "UTF-8");
                }
                catch (final java.io.UnsupportedEncodingException ex)
                {
                    throw new RuntimeException(ex);
                }

                return value;
            }

            public static int valueId()
            {
                return 20;
            }

            public static int valueSinceVersion()
            {
                return 0;
            }

            public static String valueCharacterEncoding()
            {
                return "UTF-8";
            }

            public static String valueMetaAttribute(final MetaAttribute metaAttribute)
            {
                switch (metaAttribute)
                {
                    case EPOCH: return "unix";
                    case TIME_UNIT: return "nanosecond";
                    case SEMANTIC_TYPE: return "";
                    case PRESENCE: return "required";
                }

                return "";
            }

            public static int valueHeaderLength()
            {
                return 4;
            }

            public int valueLength()
            {
                final int limit = parentMessage.limit();
                return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            }

            public int skipValue()
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                final int dataOffset = limit + headerLength;

                parentMessage.limit(dataOffset + dataLength);

                return dataLength;
            }

            public int getValue(final MutableDirectBuffer dst, final int dstOffset, final int length)
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                final int bytesCopied = Math.min(length, dataLength);
                parentMessage.limit(limit + headerLength + dataLength);
                buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

                return bytesCopied;
            }

            public int getValue(final byte[] dst, final int dstOffset, final int length)
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                final int bytesCopied = Math.min(length, dataLength);
                parentMessage.limit(limit + headerLength + dataLength);
                buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

                return bytesCopied;
            }

            public void wrapValue(final DirectBuffer wrapBuffer)
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                parentMessage.limit(limit + headerLength + dataLength);
                wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
            }

            public String value()
            {
                final int headerLength = 4;
                final int limit = parentMessage.limit();
                final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
                parentMessage.limit(limit + headerLength + dataLength);

                if (0 == dataLength)
                {
                    return "";
                }

                final byte[] tmp = new byte[dataLength];
                buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

                final String value;
                try
                {
                    value = new String(tmp, "UTF-8");
                }
                catch (final java.io.UnsupportedEncodingException ex)
                {
                    throw new RuntimeException(ex);
                }

                return value;
            }


            public String toString()
            {
                return appendTo(new StringBuilder(100)).toString();
            }

            public StringBuilder appendTo(final StringBuilder builder)
            {
                builder.append('(');
                builder.append("key=");
                builder.append('\'').append(key()).append('\'');
                builder.append('|');
                builder.append("value=");
                builder.append('\'').append(value()).append('\'');
                builder.append(')');
                return builder;
            }
        }

        public static int keyId()
        {
            return 15;
        }

        public static int keySinceVersion()
        {
            return 0;
        }

        public static String keyCharacterEncoding()
        {
            return "UTF-8";
        }

        public static String keyMetaAttribute(final MetaAttribute metaAttribute)
        {
            switch (metaAttribute)
            {
                case EPOCH: return "unix";
                case TIME_UNIT: return "nanosecond";
                case SEMANTIC_TYPE: return "";
                case PRESENCE: return "required";
            }

            return "";
        }

        public static int keyHeaderLength()
        {
            return 4;
        }

        public int keyLength()
        {
            final int limit = parentMessage.limit();
            return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        }

        public int skipKey()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int dataOffset = limit + headerLength;

            parentMessage.limit(dataOffset + dataLength);

            return dataLength;
        }

        public int getKey(final MutableDirectBuffer dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public int getKey(final byte[] dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public void wrapKey(final DirectBuffer wrapBuffer)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);
            wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
        }

        public String key()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);

            if (0 == dataLength)
            {
                return "";
            }

            final byte[] tmp = new byte[dataLength];
            buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

            final String value;
            try
            {
                value = new String(tmp, "UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            return value;
        }

        public static int valueId()
        {
            return 16;
        }

        public static int valueSinceVersion()
        {
            return 0;
        }

        public static String valueCharacterEncoding()
        {
            return "UTF-8";
        }

        public static String valueMetaAttribute(final MetaAttribute metaAttribute)
        {
            switch (metaAttribute)
            {
                case EPOCH: return "unix";
                case TIME_UNIT: return "nanosecond";
                case SEMANTIC_TYPE: return "";
                case PRESENCE: return "required";
            }

            return "";
        }

        public static int valueHeaderLength()
        {
            return 4;
        }

        public int valueLength()
        {
            final int limit = parentMessage.limit();
            return (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
        }

        public int skipValue()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int dataOffset = limit + headerLength;

            parentMessage.limit(dataOffset + dataLength);

            return dataLength;
        }

        public int getValue(final MutableDirectBuffer dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public int getValue(final byte[] dst, final int dstOffset, final int length)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            final int bytesCopied = Math.min(length, dataLength);
            parentMessage.limit(limit + headerLength + dataLength);
            buffer.getBytes(limit + headerLength, dst, dstOffset, bytesCopied);

            return bytesCopied;
        }

        public void wrapValue(final DirectBuffer wrapBuffer)
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);
            wrapBuffer.wrap(buffer, limit + headerLength, dataLength);
        }

        public String value()
        {
            final int headerLength = 4;
            final int limit = parentMessage.limit();
            final int dataLength = (int)(buffer.getInt(limit, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF_FFFFL);
            parentMessage.limit(limit + headerLength + dataLength);

            if (0 == dataLength)
            {
                return "";
            }

            final byte[] tmp = new byte[dataLength];
            buffer.getBytes(limit + headerLength, tmp, 0, dataLength);

            final String value;
            try
            {
                value = new String(tmp, "UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            return value;
        }


        public String toString()
        {
            return appendTo(new StringBuilder(100)).toString();
        }

        public StringBuilder appendTo(final StringBuilder builder)
        {
            builder.append('(');
            builder.append("headers=[");
            HeadersDecoder headers = headers();
            if (headers.count() > 0)
            {
                while (headers.hasNext())
                {
                    headers.next().appendTo(builder);
                    builder.append(',');
                }
                builder.setLength(builder.length() - 1);
            }
            builder.append(']');
            builder.append('|');
            builder.append("key=");
            builder.append('\'').append(key()).append('\'');
            builder.append('|');
            builder.append("value=");
            builder.append('\'').append(value()).append('\'');
            builder.append(')');
            return builder;
        }
    }


    public String toString()
    {
        return appendTo(new StringBuilder(100)).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        final int originalLimit = limit();
        limit(offset + actingBlockLength);
        builder.append("[MultiMessage](sbeTemplateId=");
        builder.append(TEMPLATE_ID);
        builder.append("|sbeSchemaId=");
        builder.append(SCHEMA_ID);
        builder.append("|sbeSchemaVersion=");
        if (parentMessage.actingVersion != SCHEMA_VERSION)
        {
            builder.append(parentMessage.actingVersion);
            builder.append('/');
        }
        builder.append(SCHEMA_VERSION);
        builder.append("|sbeBlockLength=");
        if (actingBlockLength != BLOCK_LENGTH)
        {
            builder.append(actingBlockLength);
            builder.append('/');
        }
        builder.append(BLOCK_LENGTH);
        builder.append("):");
        builder.append("headers=[");
        HeadersDecoder headers = headers();
        if (headers.count() > 0)
        {
            while (headers.hasNext())
            {
                headers.next().appendTo(builder);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
        builder.append(']');
        builder.append('|');
        builder.append("messages=[");
        MessagesDecoder messages = messages();
        if (messages.count() > 0)
        {
            while (messages.hasNext())
            {
                messages.next().appendTo(builder);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
        builder.append(']');

        limit(originalLimit);

        return builder;
    }
}

    
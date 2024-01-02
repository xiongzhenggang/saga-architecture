package com.xzg.orchestrator.kit.message.consumer;

import com.xzg.orchestrator.kit.message.consumer.model.MetaAttribute;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: MultiMessageEncoder
 * @author: xzg
 * @description: TODO
 * @date: 25/11/2023-上午 10:55
 * @version: 1.0
 */
public class MultiMessageEncoder
{
    public static final int BLOCK_LENGTH = 0;
    public static final int TEMPLATE_ID = 1;
    public static final int SCHEMA_ID = 1;
    public static final int SCHEMA_VERSION = 0;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private final MultiMessageEncoder parentMessage = this;
    private MutableDirectBuffer buffer;
    protected int offset;
    protected int limit;

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

    public MutableDirectBuffer buffer()
    {
        return buffer;
    }

    public int offset()
    {
        return offset;
    }

    public MultiMessageEncoder wrap(final MutableDirectBuffer buffer, final int offset)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.offset = offset;
        limit(offset + BLOCK_LENGTH);

        return this;
    }

    public MultiMessageEncoder wrapAndApplyHeader(
            final MutableDirectBuffer buffer, final int offset, final MessageHeaderEncoder headerEncoder)
    {
        headerEncoder
                .wrap(buffer, offset)
                .blockLength(BLOCK_LENGTH)
                .templateId(TEMPLATE_ID)
                .schemaId(SCHEMA_ID)
                .version(SCHEMA_VERSION);

        return wrap(buffer, offset + MessageHeaderEncoder.ENCODED_LENGTH);
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

    private final HeadersEncoder headers = new HeadersEncoder(this);

    public static long headersId()
    {
        return 13;
    }

    public HeadersEncoder headersCount(final int count)
    {
        headers.wrap(buffer, count);
        return headers;
    }

    public static class HeadersEncoder
    {
        public static final int HEADER_SIZE = 4;
        private final MultiMessageEncoder parentMessage;
        private MutableDirectBuffer buffer;
        private int count;
        private int index;
        private int offset;

        HeadersEncoder(final MultiMessageEncoder parentMessage)
        {
            this.parentMessage = parentMessage;
        }

        public void wrap(final MutableDirectBuffer buffer, final int count)
        {
            if (count < 0 || count > 65534)
            {
                throw new IllegalArgumentException("count outside allowed range: count=" + count);
            }

            if (buffer != this.buffer)
            {
                this.buffer = buffer;
            }

            index = -1;
            this.count = count;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + HEADER_SIZE);
            buffer.putShort(limit + 0, (short)(int)0, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putShort(limit + 2, (short)(int)count, java.nio.ByteOrder.LITTLE_ENDIAN);
        }

        public static int sbeHeaderSize()
        {
            return HEADER_SIZE;
        }

        public static int sbeBlockLength()
        {
            return 0;
        }

        public HeadersEncoder next()
        {
            if (index + 1 >= count)
            {
                throw new java.util.NoSuchElementException();
            }

            offset = parentMessage.limit();
            parentMessage.limit(offset + sbeBlockLength());
            ++index;

            return this;
        }

        public static int keyId()
        {
            return 17;
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

        public HeadersEncoder putKey(final DirectBuffer src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public HeadersEncoder putKey(final byte[] src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public HeadersEncoder key(final String value)
        {
            final byte[] bytes;
            try
            {
                bytes = null == value || value.isEmpty() ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes("UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            final int length = bytes.length;
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, bytes, 0, length);

            return this;
        }

        public static int valueId()
        {
            return 18;
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

        public HeadersEncoder putValue(final DirectBuffer src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public HeadersEncoder putValue(final byte[] src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public HeadersEncoder value(final String value)
        {
            final byte[] bytes;
            try
            {
                bytes = null == value || value.isEmpty() ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes("UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            final int length = bytes.length;
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, bytes, 0, length);

            return this;
        }
    }

    private final MessagesEncoder messages = new MessagesEncoder(this);

    public static long messagesId()
    {
        return 12;
    }

    public MessagesEncoder messagesCount(final int count)
    {
        messages.wrap(buffer, count);
        return messages;
    }

    public static class MessagesEncoder
    {
        public static final int HEADER_SIZE = 4;
        private final MultiMessageEncoder parentMessage;
        private MutableDirectBuffer buffer;
        private int count;
        private int index;
        private int offset;
        private final HeadersEncoder headers;

        MessagesEncoder(final MultiMessageEncoder parentMessage)
        {
            this.parentMessage = parentMessage;
            headers = new HeadersEncoder(parentMessage);
        }

        public void wrap(final MutableDirectBuffer buffer, final int count)
        {
            if (count < 0 || count > 65534)
            {
                throw new IllegalArgumentException("count outside allowed range: count=" + count);
            }

            if (buffer != this.buffer)
            {
                this.buffer = buffer;
            }

            index = -1;
            this.count = count;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + HEADER_SIZE);
            buffer.putShort(limit + 0, (short)(int)0, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putShort(limit + 2, (short)(int)count, java.nio.ByteOrder.LITTLE_ENDIAN);
        }

        public static int sbeHeaderSize()
        {
            return HEADER_SIZE;
        }

        public static int sbeBlockLength()
        {
            return 0;
        }

        public MessagesEncoder next()
        {
            if (index + 1 >= count)
            {
                throw new java.util.NoSuchElementException();
            }

            offset = parentMessage.limit();
            parentMessage.limit(offset + sbeBlockLength());
            ++index;

            return this;
        }

        public static long headersId()
        {
            return 14;
        }

        public HeadersEncoder headersCount(final int count)
        {
            headers.wrap(buffer, count);
            return headers;
        }

        public static class HeadersEncoder
        {
            public static final int HEADER_SIZE = 4;
            private final MultiMessageEncoder parentMessage;
            private MutableDirectBuffer buffer;
            private int count;
            private int index;
            private int offset;

            HeadersEncoder(final MultiMessageEncoder parentMessage)
            {
                this.parentMessage = parentMessage;
            }

            public void wrap(final MutableDirectBuffer buffer, final int count)
            {
                if (count < 0 || count > 65534)
                {
                    throw new IllegalArgumentException("count outside allowed range: count=" + count);
                }

                if (buffer != this.buffer)
                {
                    this.buffer = buffer;
                }

                index = -1;
                this.count = count;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + HEADER_SIZE);
                buffer.putShort(limit + 0, (short)(int)0, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putShort(limit + 2, (short)(int)count, java.nio.ByteOrder.LITTLE_ENDIAN);
            }

            public static int sbeHeaderSize()
            {
                return HEADER_SIZE;
            }

            public static int sbeBlockLength()
            {
                return 0;
            }

            public HeadersEncoder next()
            {
                if (index + 1 >= count)
                {
                    throw new java.util.NoSuchElementException();
                }

                offset = parentMessage.limit();
                parentMessage.limit(offset + sbeBlockLength());
                ++index;

                return this;
            }

            public static int keyId()
            {
                return 19;
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

            public HeadersEncoder putKey(final DirectBuffer src, final int srcOffset, final int length)
            {
                if (length > 1073741824)
                {
                    throw new IllegalStateException("length > maxValue for type: " + length);
                }

                final int headerLength = 4;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + headerLength + length);
                buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putBytes(limit + headerLength, src, srcOffset, length);

                return this;
            }

            public HeadersEncoder putKey(final byte[] src, final int srcOffset, final int length)
            {
                if (length > 1073741824)
                {
                    throw new IllegalStateException("length > maxValue for type: " + length);
                }

                final int headerLength = 4;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + headerLength + length);
                buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putBytes(limit + headerLength, src, srcOffset, length);

                return this;
            }

            public HeadersEncoder key(final String value)
            {
                final byte[] bytes;
                try
                {
                    bytes = null == value || value.isEmpty() ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes("UTF-8");
                }
                catch (final java.io.UnsupportedEncodingException ex)
                {
                    throw new RuntimeException(ex);
                }

                final int length = bytes.length;
                if (length > 1073741824)
                {
                    throw new IllegalStateException("length > maxValue for type: " + length);
                }

                final int headerLength = 4;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + headerLength + length);
                buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putBytes(limit + headerLength, bytes, 0, length);

                return this;
            }

            public static int valueId()
            {
                return 20;
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

            public HeadersEncoder putValue(final DirectBuffer src, final int srcOffset, final int length)
            {
                if (length > 1073741824)
                {
                    throw new IllegalStateException("length > maxValue for type: " + length);
                }

                final int headerLength = 4;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + headerLength + length);
                buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putBytes(limit + headerLength, src, srcOffset, length);

                return this;
            }

            public HeadersEncoder putValue(final byte[] src, final int srcOffset, final int length)
            {
                if (length > 1073741824)
                {
                    throw new IllegalStateException("length > maxValue for type: " + length);
                }

                final int headerLength = 4;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + headerLength + length);
                buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putBytes(limit + headerLength, src, srcOffset, length);

                return this;
            }

            public HeadersEncoder value(final String value)
            {
                final byte[] bytes;
                try
                {
                    bytes = null == value || value.isEmpty() ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes("UTF-8");
                }
                catch (final java.io.UnsupportedEncodingException ex)
                {
                    throw new RuntimeException(ex);
                }

                final int length = bytes.length;
                if (length > 1073741824)
                {
                    throw new IllegalStateException("length > maxValue for type: " + length);
                }

                final int headerLength = 4;
                final int limit = parentMessage.limit();
                parentMessage.limit(limit + headerLength + length);
                buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
                buffer.putBytes(limit + headerLength, bytes, 0, length);

                return this;
            }
        }

        public static int keyId()
        {
            return 15;
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

        public MessagesEncoder putKey(final DirectBuffer src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public MessagesEncoder putKey(final byte[] src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public MessagesEncoder key(final String value)
        {
            final byte[] bytes;
            try
            {
                bytes = null == value || value.isEmpty() ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes("UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            final int length = bytes.length;
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, bytes, 0, length);

            return this;
        }

        public static int valueId()
        {
            return 16;
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

        public MessagesEncoder putValue(final DirectBuffer src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public MessagesEncoder putValue(final byte[] src, final int srcOffset, final int length)
        {
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, src, srcOffset, length);

            return this;
        }

        public MessagesEncoder value(final String value)
        {
            final byte[] bytes;
            try
            {
                bytes = null == value || value.isEmpty() ? org.agrona.collections.ArrayUtil.EMPTY_BYTE_ARRAY : value.getBytes("UTF-8");
            }
            catch (final java.io.UnsupportedEncodingException ex)
            {
                throw new RuntimeException(ex);
            }

            final int length = bytes.length;
            if (length > 1073741824)
            {
                throw new IllegalStateException("length > maxValue for type: " + length);
            }

            final int headerLength = 4;
            final int limit = parentMessage.limit();
            parentMessage.limit(limit + headerLength + length);
            buffer.putInt(limit, (int)length, java.nio.ByteOrder.LITTLE_ENDIAN);
            buffer.putBytes(limit + headerLength, bytes, 0, length);

            return this;
        }
    }


    public String toString()
    {
        return appendTo(new StringBuilder(100)).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        MultiMessageDecoder writer = new MultiMessageDecoder();
        writer.wrap(buffer, offset, BLOCK_LENGTH, SCHEMA_VERSION);

        return writer.appendTo(builder);
    }
}


    
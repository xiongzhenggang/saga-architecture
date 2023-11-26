package com.xzg.orchestrator.kit.event.consumer;

import org.agrona.DirectBuffer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: MessageHeaderDecoder
 * @author: xzg
 * @description: TODO
 * @date: 24/11/2023-下午 8:29
 * @version: 1.0
 */
public class MessageHeaderDecoder
{
    public static final int SCHEMA_ID = 1;
    public static final int SCHEMA_VERSION = 0;
    public static final int ENCODED_LENGTH = 40;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private int offset;
    private DirectBuffer buffer;

    public MessageHeaderDecoder wrap(final DirectBuffer buffer, final int offset)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.offset = offset;

        return this;
    }

    public DirectBuffer buffer()
    {
        return buffer;
    }

    public int offset()
    {
        return offset;
    }

    public int encodedLength()
    {
        return ENCODED_LENGTH;
    }

    public int sbeSchemaId()
    {
        return SCHEMA_ID;
    }

    public int sbeSchemaVersion()
    {
        return SCHEMA_VERSION;
    }

    public static int magicBytesEncodingOffset()
    {
        return 0;
    }

    public static int magicBytesEncodingLength()
    {
        return 32;
    }

    public static int magicBytesSinceVersion()
    {
        return 0;
    }

    public static short magicBytesNullValue()
    {
        return (short)255;
    }

    public static short magicBytesMinValue()
    {
        return (short)0;
    }

    public static short magicBytesMaxValue()
    {
        return (short)254;
    }

    public static int magicBytesLength()
    {
        return 32;
    }


    public short magicBytes(final int index)
    {
        if (index < 0 || index >= 32)
        {
            throw new IndexOutOfBoundsException("index out of range: index=" + index);
        }

        final int pos = this.offset + 0 + (index * 1);

        return ((short)(buffer.getByte(pos) & 0xFF));
    }


    public static int blockLengthEncodingOffset()
    {
        return 32;
    }

    public static int blockLengthEncodingLength()
    {
        return 2;
    }

    public static int blockLengthSinceVersion()
    {
        return 0;
    }

    public static int blockLengthNullValue()
    {
        return 65535;
    }

    public static int blockLengthMinValue()
    {
        return 0;
    }

    public static int blockLengthMaxValue()
    {
        return 65534;
    }

    public int blockLength()
    {
        return (buffer.getShort(offset + 32, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
    }


    public static int templateIdEncodingOffset()
    {
        return 34;
    }

    public static int templateIdEncodingLength()
    {
        return 2;
    }

    public static int templateIdSinceVersion()
    {
        return 0;
    }

    public static int templateIdNullValue()
    {
        return 65535;
    }

    public static int templateIdMinValue()
    {
        return 0;
    }

    public static int templateIdMaxValue()
    {
        return 65534;
    }

    public int templateId()
    {
        return (buffer.getShort(offset + 34, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
    }


    public static int schemaIdEncodingOffset()
    {
        return 36;
    }

    public static int schemaIdEncodingLength()
    {
        return 2;
    }

    public static int schemaIdSinceVersion()
    {
        return 0;
    }

    public static int schemaIdNullValue()
    {
        return 65535;
    }

    public static int schemaIdMinValue()
    {
        return 0;
    }

    public static int schemaIdMaxValue()
    {
        return 65534;
    }

    public int schemaId()
    {
        return (buffer.getShort(offset + 36, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
    }


    public static int versionEncodingOffset()
    {
        return 38;
    }

    public static int versionEncodingLength()
    {
        return 2;
    }

    public static int versionSinceVersion()
    {
        return 0;
    }

    public static int versionNullValue()
    {
        return 65535;
    }

    public static int versionMinValue()
    {
        return 0;
    }

    public static int versionMaxValue()
    {
        return 65534;
    }

    public int version()
    {
        return (buffer.getShort(offset + 38, java.nio.ByteOrder.LITTLE_ENDIAN) & 0xFFFF);
    }


    public String toString()
    {
        return appendTo(new StringBuilder(100)).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        builder.append('(');
        builder.append("magicBytes=");
        builder.append('[');
        if (magicBytesLength() > 0)
        {
            for (int i = 0; i < magicBytesLength(); i++)
            {
                builder.append(magicBytes(i));
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
        builder.append(']');
        builder.append('|');
        builder.append("blockLength=");
        builder.append(blockLength());
        builder.append('|');
        builder.append("templateId=");
        builder.append(templateId());
        builder.append('|');
        builder.append("schemaId=");
        builder.append(schemaId());
        builder.append('|');
        builder.append("version=");
        builder.append(version());
        builder.append(')');

        return builder;
    }
}

    
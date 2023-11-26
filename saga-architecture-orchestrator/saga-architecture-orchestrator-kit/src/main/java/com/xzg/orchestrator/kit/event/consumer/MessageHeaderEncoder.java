package com.xzg.orchestrator.kit.event.consumer;

import org.agrona.MutableDirectBuffer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: MessageHeaderEncoder
 * @author: xzg
 * @description: TODO
 * @date: 24/11/2023-下午 8:26
 * @version: 1.0
 */
public class MessageHeaderEncoder
{
    public static final int SCHEMA_ID = 1;
    public static final int SCHEMA_VERSION = 0;
    public static final int ENCODED_LENGTH = 40;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private int offset;
    private MutableDirectBuffer buffer;

    public MessageHeaderEncoder wrap(final MutableDirectBuffer buffer, final int offset)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.offset = offset;

        return this;
    }

    public MutableDirectBuffer buffer()
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


    public MessageHeaderEncoder magicBytes(final int index, final short value)
    {
        if (index < 0 || index >= 32)
        {
            throw new IndexOutOfBoundsException("index out of range: index=" + index);
        }

        final int pos = this.offset + 0 + (index * 1);
        buffer.putByte(pos, (byte)value);

        return this;
    }

    public static int blockLengthEncodingOffset()
    {
        return 32;
    }

    public static int blockLengthEncodingLength()
    {
        return 2;
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

    public MessageHeaderEncoder blockLength(final int value)
    {
        buffer.putShort(offset + 32, (short)value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
    }


    public static int templateIdEncodingOffset()
    {
        return 34;
    }

    public static int templateIdEncodingLength()
    {
        return 2;
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

    public MessageHeaderEncoder templateId(final int value)
    {
        buffer.putShort(offset + 34, (short)value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
    }


    public static int schemaIdEncodingOffset()
    {
        return 36;
    }

    public static int schemaIdEncodingLength()
    {
        return 2;
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

    public MessageHeaderEncoder schemaId(final int value)
    {
        buffer.putShort(offset + 36, (short)value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
    }


    public static int versionEncodingOffset()
    {
        return 38;
    }

    public static int versionEncodingLength()
    {
        return 2;
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

    public MessageHeaderEncoder version(final int value)
    {
        buffer.putShort(offset + 38, (short)value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
    }


    public String toString()
    {
        return appendTo(new StringBuilder(100)).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        MessageHeaderDecoder writer = new MessageHeaderDecoder();
        writer.wrap(buffer, offset);

        return writer.appendTo(builder);
    }
}


    
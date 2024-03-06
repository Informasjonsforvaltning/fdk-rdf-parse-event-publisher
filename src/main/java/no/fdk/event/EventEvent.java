/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package no.fdk.event;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;

@org.apache.avro.specific.AvroGenerated
public class EventEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EventEvent\",\"namespace\":\"no.fdk.event\",\"fields\":[{\"name\":\"type\",\"type\":{\"type\":\"enum\",\"name\":\"EventEventType\",\"symbols\":[\"EVENT_HARVESTED\",\"EVENT_REASONED\",\"EVENT_REMOVED\"]}},{\"name\":\"fdkId\",\"type\":\"string\"},{\"name\":\"graph\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"long\"}]}");
    private static final long serialVersionUID = 8858484226904336348L;
    private static final SpecificData MODEL$ = new SpecificData();
    private static final BinaryMessageEncoder<EventEvent> ENCODER =
            new BinaryMessageEncoder<>(MODEL$, SCHEMA$);
    private static final BinaryMessageDecoder<EventEvent> DECODER =
            new BinaryMessageDecoder<>(MODEL$, SCHEMA$);
    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumWriter<EventEvent>
            WRITER$ = (org.apache.avro.io.DatumWriter<EventEvent>) MODEL$.createDatumWriter(SCHEMA$);
    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumReader<EventEvent>
            READER$ = (org.apache.avro.io.DatumReader<EventEvent>) MODEL$.createDatumReader(SCHEMA$);
    private no.fdk.event.EventEventType type;
    private java.lang.CharSequence fdkId;
    private java.lang.CharSequence graph;
    private long timestamp;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public EventEvent() {
    }

    /**
     * All-args constructor.
     *
     * @param type      The new value for type
     * @param fdkId     The new value for fdkId
     * @param graph     The new value for graph
     * @param timestamp The new value for timestamp
     */
    public EventEvent(no.fdk.event.EventEventType type, java.lang.CharSequence fdkId, java.lang.CharSequence graph, java.lang.Long timestamp) {
        this.type = type;
        this.fdkId = fdkId;
        this.graph = graph;
        this.timestamp = timestamp;
    }

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    /**
     * Return the BinaryMessageEncoder instance used by this class.
     *
     * @return the message encoder used by this class
     */
    public static BinaryMessageEncoder<EventEvent> getEncoder() {
        return ENCODER;
    }

    /**
     * Return the BinaryMessageDecoder instance used by this class.
     *
     * @return the message decoder used by this class
     */
    public static BinaryMessageDecoder<EventEvent> getDecoder() {
        return DECODER;
    }

    /**
     * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
     *
     * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
     * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
     */
    public static BinaryMessageDecoder<EventEvent> createDecoder(SchemaStore resolver) {
        return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
    }

    /**
     * Deserializes a EventEvent from a ByteBuffer.
     *
     * @param b a byte buffer holding serialized data for an instance of this class
     * @return a EventEvent instance decoded from the given buffer
     * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
     */
    public static EventEvent fromByteBuffer(
            java.nio.ByteBuffer b) throws java.io.IOException {
        return DECODER.decode(b);
    }

    /**
     * Creates a new EventEvent RecordBuilder.
     *
     * @return A new EventEvent RecordBuilder
     */
    public static no.fdk.event.EventEvent.Builder newBuilder() {
        return new no.fdk.event.EventEvent.Builder();
    }

    /**
     * Creates a new EventEvent RecordBuilder by copying an existing Builder.
     *
     * @param other The existing builder to copy.
     * @return A new EventEvent RecordBuilder
     */
    public static no.fdk.event.EventEvent.Builder newBuilder(no.fdk.event.EventEvent.Builder other) {
        if (other == null) {
            return new no.fdk.event.EventEvent.Builder();
        } else {
            return new no.fdk.event.EventEvent.Builder(other);
        }
    }

    /**
     * Creates a new EventEvent RecordBuilder by copying an existing EventEvent instance.
     *
     * @param other The existing instance to copy.
     * @return A new EventEvent RecordBuilder
     */
    public static no.fdk.event.EventEvent.Builder newBuilder(no.fdk.event.EventEvent other) {
        if (other == null) {
            return new no.fdk.event.EventEvent.Builder();
        } else {
            return new no.fdk.event.EventEvent.Builder(other);
        }
    }

    /**
     * Serializes this EventEvent to a ByteBuffer.
     *
     * @return a buffer holding the serialized data for this instance
     * @throws java.io.IOException if this instance could not be serialized
     */
    public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
        return ENCODER.encode(this);
    }

    @Override
    public org.apache.avro.specific.SpecificData getSpecificData() {
        return MODEL$;
    }

    @Override
    public org.apache.avro.Schema getSchema() {
        return SCHEMA$;
    }

    // Used by DatumWriter.  Applications should not call.
    @Override
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0:
                return type;
            case 1:
                return fdkId;
            case 2:
                return graph;
            case 3:
                return timestamp;
            default:
                throw new IndexOutOfBoundsException("Invalid index: " + field$);
        }
    }

    // Used by DatumReader.  Applications should not call.
    @Override
    @SuppressWarnings(value = "unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0:
                type = (no.fdk.event.EventEventType) value$;
                break;
            case 1:
                fdkId = (java.lang.CharSequence) value$;
                break;
            case 2:
                graph = (java.lang.CharSequence) value$;
                break;
            case 3:
                timestamp = (java.lang.Long) value$;
                break;
            default:
                throw new IndexOutOfBoundsException("Invalid index: " + field$);
        }
    }

    /**
     * Gets the value of the 'type' field.
     *
     * @return The value of the 'type' field.
     */
    public no.fdk.event.EventEventType getType() {
        return type;
    }

    /**
     * Sets the value of the 'type' field.
     *
     * @param value the value to set.
     */
    public void setType(no.fdk.event.EventEventType value) {
        this.type = value;
    }

    /**
     * Gets the value of the 'fdkId' field.
     *
     * @return The value of the 'fdkId' field.
     */
    public java.lang.CharSequence getFdkId() {
        return fdkId;
    }

    /**
     * Sets the value of the 'fdkId' field.
     *
     * @param value the value to set.
     */
    public void setFdkId(java.lang.CharSequence value) {
        this.fdkId = value;
    }

    /**
     * Gets the value of the 'graph' field.
     *
     * @return The value of the 'graph' field.
     */
    public java.lang.CharSequence getGraph() {
        return graph;
    }

    /**
     * Sets the value of the 'graph' field.
     *
     * @param value the value to set.
     */
    public void setGraph(java.lang.CharSequence value) {
        this.graph = value;
    }

    /**
     * Gets the value of the 'timestamp' field.
     *
     * @return The value of the 'timestamp' field.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the 'timestamp' field.
     *
     * @param value the value to set.
     */
    public void setTimestamp(long value) {
        this.timestamp = value;
    }

    @Override
    public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    @Override
    public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

    @Override
    protected boolean hasCustomCoders() {
        return true;
    }

    @Override
    public void customEncode(org.apache.avro.io.Encoder out)
            throws java.io.IOException {
        out.writeEnum(this.type.ordinal());

        out.writeString(this.fdkId);

        out.writeString(this.graph);

        out.writeLong(this.timestamp);

    }

    @Override
    public void customDecode(org.apache.avro.io.ResolvingDecoder in)
            throws java.io.IOException {
        org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
        if (fieldOrder == null) {
            this.type = no.fdk.event.EventEventType.values()[in.readEnum()];

            this.fdkId = in.readString(this.fdkId instanceof Utf8 ? (Utf8) this.fdkId : null);

            this.graph = in.readString(this.graph instanceof Utf8 ? (Utf8) this.graph : null);

            this.timestamp = in.readLong();

        } else {
            for (int i = 0; i < 4; i++) {
                switch (fieldOrder[i].pos()) {
                    case 0:
                        this.type = no.fdk.event.EventEventType.values()[in.readEnum()];
                        break;

                    case 1:
                        this.fdkId = in.readString(this.fdkId instanceof Utf8 ? (Utf8) this.fdkId : null);
                        break;

                    case 2:
                        this.graph = in.readString(this.graph instanceof Utf8 ? (Utf8) this.graph : null);
                        break;

                    case 3:
                        this.timestamp = in.readLong();
                        break;

                    default:
                        throw new java.io.IOException("Corrupt ResolvingDecoder.");
                }
            }
        }
    }

    /**
     * RecordBuilder for EventEvent instances.
     */
    @org.apache.avro.specific.AvroGenerated
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<EventEvent>
            implements org.apache.avro.data.RecordBuilder<EventEvent> {

        private no.fdk.event.EventEventType type;
        private java.lang.CharSequence fdkId;
        private java.lang.CharSequence graph;
        private long timestamp;

        /**
         * Creates a new Builder
         */
        private Builder() {
            super(SCHEMA$, MODEL$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         *
         * @param other The existing Builder to copy.
         */
        private Builder(no.fdk.event.EventEvent.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.type)) {
                this.type = data().deepCopy(fields()[0].schema(), other.type);
                fieldSetFlags()[0] = other.fieldSetFlags()[0];
            }
            if (isValidValue(fields()[1], other.fdkId)) {
                this.fdkId = data().deepCopy(fields()[1].schema(), other.fdkId);
                fieldSetFlags()[1] = other.fieldSetFlags()[1];
            }
            if (isValidValue(fields()[2], other.graph)) {
                this.graph = data().deepCopy(fields()[2].schema(), other.graph);
                fieldSetFlags()[2] = other.fieldSetFlags()[2];
            }
            if (isValidValue(fields()[3], other.timestamp)) {
                this.timestamp = data().deepCopy(fields()[3].schema(), other.timestamp);
                fieldSetFlags()[3] = other.fieldSetFlags()[3];
            }
        }

        /**
         * Creates a Builder by copying an existing EventEvent instance
         *
         * @param other The existing instance to copy.
         */
        private Builder(no.fdk.event.EventEvent other) {
            super(SCHEMA$, MODEL$);
            if (isValidValue(fields()[0], other.type)) {
                this.type = data().deepCopy(fields()[0].schema(), other.type);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.fdkId)) {
                this.fdkId = data().deepCopy(fields()[1].schema(), other.fdkId);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.graph)) {
                this.graph = data().deepCopy(fields()[2].schema(), other.graph);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.timestamp)) {
                this.timestamp = data().deepCopy(fields()[3].schema(), other.timestamp);
                fieldSetFlags()[3] = true;
            }
        }

        /**
         * Gets the value of the 'type' field.
         *
         * @return The value.
         */
        public no.fdk.event.EventEventType getType() {
            return type;
        }


        /**
         * Sets the value of the 'type' field.
         *
         * @param value The value of 'type'.
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder setType(no.fdk.event.EventEventType value) {
            validate(fields()[0], value);
            this.type = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'type' field has been set.
         *
         * @return True if the 'type' field has been set, false otherwise.
         */
        public boolean hasType() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'type' field.
         *
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder clearType() {
            type = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'fdkId' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getFdkId() {
            return fdkId;
        }


        /**
         * Sets the value of the 'fdkId' field.
         *
         * @param value The value of 'fdkId'.
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder setFdkId(java.lang.CharSequence value) {
            validate(fields()[1], value);
            this.fdkId = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'fdkId' field has been set.
         *
         * @return True if the 'fdkId' field has been set, false otherwise.
         */
        public boolean hasFdkId() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'fdkId' field.
         *
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder clearFdkId() {
            fdkId = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'graph' field.
         *
         * @return The value.
         */
        public java.lang.CharSequence getGraph() {
            return graph;
        }


        /**
         * Sets the value of the 'graph' field.
         *
         * @param value The value of 'graph'.
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder setGraph(java.lang.CharSequence value) {
            validate(fields()[2], value);
            this.graph = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'graph' field has been set.
         *
         * @return True if the 'graph' field has been set, false otherwise.
         */
        public boolean hasGraph() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'graph' field.
         *
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder clearGraph() {
            graph = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        /**
         * Gets the value of the 'timestamp' field.
         *
         * @return The value.
         */
        public long getTimestamp() {
            return timestamp;
        }


        /**
         * Sets the value of the 'timestamp' field.
         *
         * @param value The value of 'timestamp'.
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder setTimestamp(long value) {
            validate(fields()[3], value);
            this.timestamp = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        /**
         * Checks whether the 'timestamp' field has been set.
         *
         * @return True if the 'timestamp' field has been set, false otherwise.
         */
        public boolean hasTimestamp() {
            return fieldSetFlags()[3];
        }


        /**
         * Clears the value of the 'timestamp' field.
         *
         * @return This builder.
         */
        public no.fdk.event.EventEvent.Builder clearTimestamp() {
            fieldSetFlags()[3] = false;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public EventEvent build() {
            try {
                EventEvent record = new EventEvent();
                record.type = fieldSetFlags()[0] ? this.type : (no.fdk.event.EventEventType) defaultValue(fields()[0]);
                record.fdkId = fieldSetFlags()[1] ? this.fdkId : (java.lang.CharSequence) defaultValue(fields()[1]);
                record.graph = fieldSetFlags()[2] ? this.graph : (java.lang.CharSequence) defaultValue(fields()[2]);
                record.timestamp = fieldSetFlags()[3] ? this.timestamp : (java.lang.Long) defaultValue(fields()[3]);
                return record;
            } catch (org.apache.avro.AvroMissingFieldException e) {
                throw e;
            } catch (java.lang.Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }
}











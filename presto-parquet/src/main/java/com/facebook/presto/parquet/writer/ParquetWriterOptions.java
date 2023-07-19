/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.parquet.writer;

import io.airlift.units.DataSize;
import org.apache.parquet.column.ParquetProperties.WriterVersion;

import static java.lang.Math.toIntExact;
import static java.util.Objects.requireNonNull;

public class ParquetWriterOptions
{
    private static final WriterVersion DEFAULT_FORMAT_VERSION = WriterVersion.PARQUET_2_0;
    private static final DataSize DEFAULT_MAX_ROW_GROUP_SIZE = DataSize.valueOf("128MB");
    private static final DataSize DEFAULT_MAX_PAGE_SIZE = DataSize.valueOf("1MB");

    public static ParquetWriterOptions.Builder builder()
    {
        return new ParquetWriterOptions.Builder();
    }

    private final WriterVersion writerVersion;
    private final int maxRowGroupSize;
    private final int maxPageSize;

    private ParquetWriterOptions(WriterVersion writerVersion, DataSize maxRowGroupSize, DataSize maxPageSize)
    {
        this.writerVersion = writerVersion;
        this.maxRowGroupSize = toIntExact(requireNonNull(maxRowGroupSize, "maxRowGroupSize is null").toBytes());
        this.maxPageSize = toIntExact(requireNonNull(maxPageSize, "maxPageSize is null").toBytes());
    }

    public WriterVersion getWriterVersion()
    {
        return writerVersion;
    }

    public int getMaxRowGroupSize()
    {
        return maxRowGroupSize;
    }

    public int getMaxPageSize()
    {
        return maxPageSize;
    }

    public static class Builder
    {
        private WriterVersion writerVersion = DEFAULT_FORMAT_VERSION;
        private DataSize maxBlockSize = DEFAULT_MAX_ROW_GROUP_SIZE;
        private DataSize maxPageSize = DEFAULT_MAX_PAGE_SIZE;

        public Builder setWriterVersion(WriterVersion formatVersion)
        {
            this.writerVersion = formatVersion;
            return this;
        }

        public Builder setMaxBlockSize(DataSize maxBlockSize)
        {
            this.maxBlockSize = maxBlockSize;
            return this;
        }

        public Builder setMaxPageSize(DataSize maxPageSize)
        {
            this.maxPageSize = maxPageSize;
            return this;
        }

        public ParquetWriterOptions build()
        {
            return new ParquetWriterOptions(writerVersion, maxBlockSize, maxPageSize);
        }
    }
}

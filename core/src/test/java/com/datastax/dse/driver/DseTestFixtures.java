/*
 * Copyright DataStax, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.dse.driver;

import com.datastax.dse.protocol.internal.response.result.DseRowsMetadata;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableList;
import com.datastax.oss.protocol.internal.ProtocolConstants;
import com.datastax.oss.protocol.internal.response.result.ColumnSpec;
import com.datastax.oss.protocol.internal.response.result.DefaultRows;
import com.datastax.oss.protocol.internal.response.result.RawType;
import com.datastax.oss.protocol.internal.response.result.Rows;
import com.datastax.oss.protocol.internal.util.Bytes;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class DseTestFixtures {

  // Returns a single row, with a single "message" column with the value "hello, world"
  public static Rows singleDseRow() {
    DseRowsMetadata metadata =
        new DseRowsMetadata(
            ImmutableList.of(
                new ColumnSpec(
                    "ks",
                    "table",
                    "message",
                    0,
                    RawType.PRIMITIVES.get(ProtocolConstants.DataType.VARCHAR))),
            null,
            new int[] {},
            null,
            1,
            true);
    Queue<List<ByteBuffer>> data = new ArrayDeque<>();
    data.add(ImmutableList.of(Bytes.fromHexString("0x68656C6C6F2C20776F726C64")));
    return new DefaultRows(metadata, data);
  }

  // Returns 10 rows, each with a single "message" column with the value "hello, world"
  public static Rows tenDseRows(int page, boolean last) {
    DseRowsMetadata metadata =
        new DseRowsMetadata(
            ImmutableList.of(
                new ColumnSpec(
                    "ks",
                    "table",
                    "message",
                    0,
                    RawType.PRIMITIVES.get(ProtocolConstants.DataType.VARCHAR))),
            last ? null : ByteBuffer.wrap(new byte[] {(byte) page}),
            new int[] {},
            null,
            page,
            last);
    Queue<List<ByteBuffer>> data = new ArrayDeque<>();
    for (int i = 0; i < 10; i++) {
      data.add(ImmutableList.of(Bytes.fromHexString("0x68656C6C6F2C20776F726C64")));
    }
    return new DefaultRows(metadata, data);
  }
}

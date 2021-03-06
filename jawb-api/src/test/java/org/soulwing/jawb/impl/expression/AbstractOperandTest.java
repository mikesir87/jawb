/*
 * File created on Dec 23, 2013 
 *
 * Copyright (c) 2013 Carl Harris, Jr.
 *
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
 *
 */
package org.soulwing.jawb.impl.expression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.impl.expression.AbstractOperand;
import org.soulwing.jawb.impl.expression.Value;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link AbstractOperand}.
 *
 * @author Carl Harris
 */
public class AbstractOperandTest {

  private MockOperand operand = new MockOperand();
  
  @Test
  public void testWithValue() throws Exception {
    operand.value = new Value();
    assertThat(operand.evaluate(null), sameInstance(operand.value));
  }

  @Test
  public void testWithRuntimeException() throws Exception {
    operand.ex = new RuntimeException();
    Value result = operand.evaluate(null);
    assertThat(result.getType(), equalTo(Value.Type.ERROR));
    assertThat(result.getValue(), sameInstance((Object) operand.ex));
  }

  public static class MockOperand extends AbstractOperand {

    public RuntimeException ex;
    public Value value;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Value doEvaluate(BoundWorkbook workbook)
        throws RuntimeException, WorkbookBindingException {
      if (ex != null) throw ex;
      return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(BoundWorkbook workbook) {
      throw new UnsupportedOperationException();
    }
    
  }
  
}

/*
 * File created on Dec 23, 2013 
 *
 * Copyright (c) 2013 Virginia Polytechnic Institute and State University
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
package edu.vt.ras.jawb.impl.expression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link NotOperator}.
 *
 * @author Carl Harris
 */
public class NotOperatorTest {

  private Mockery mockery = new Mockery();
    
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private Operand operand = mockery.mock(Operand.class);
  
  private NotOperator operator = new NotOperator(operand);
  
  @Test
  public void testEvaluate() throws Exception {
    final Value result = new Value(Value.Type.BOOLEAN, false);
    mockery.checking(new Expectations() { { 
      oneOf(operand).evaluate(workbook);
      will(returnValue(result));      
    } });
    
    Value value = operator.evaluate(workbook);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));
    mockery.assertIsSatisfied();
  }

}

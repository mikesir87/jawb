/*
 * File created on Dec 20, 2013 
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
package org.soulwing.jawb.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.TypeMismatchException;
import org.soulwing.jawb.impl.DateCellEvaluatorStrategy;
import org.soulwing.jawb.spi.BoundCell;

/**
 * Unit tests for {@link DateCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class DateCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCell cell = mockery.mock(BoundCell.class);

  @Test
  public void testEvaluateWithDateValue() throws Exception {
    final Date result = new Date();
    mockery.checking(new Expectations() { { 
      oneOf(cell).getDateValue();
      will(returnValue(result));
    } } );
       
    assertThat(DateCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Date.class), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testEvaluateWithNonDateValue() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(cell).getDateValue();
      will(throwException(new IllegalStateException()));
      allowing(cell).getReference();
    } } );
       
    try {
      DateCellEvaluatorStrategy.INSTANCE.evaluate(cell, Date.class);
      fail("expected TypeMismatchException");
    }
    catch (TypeMismatchException ex) {
      mockery.assertIsSatisfied();
    }
  }

}

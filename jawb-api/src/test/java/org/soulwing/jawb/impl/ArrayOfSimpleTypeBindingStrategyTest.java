/*
 * File created on Dec 21, 2013 
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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.Cell;
import org.soulwing.jawb.annotation.CellFormat;
import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.IterateSheets;
import org.soulwing.jawb.impl.ArrayOfSimpleTypeBindingStrategy;
import org.soulwing.jawb.impl.AttributeIntrospector;
import org.soulwing.jawb.impl.Binding;
import org.soulwing.jawb.impl.ColumnIterator;
import org.soulwing.jawb.impl.EvaluatorFactory;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link ArrayOfSimpleTypeBindingStrategy}.
 *
 * @author Carl Harris
 */
public class ArrayOfSimpleTypeBindingStrategyTest {

  private Mockery mockery = new Mockery();
  
  private AttributeIntrospector introspector =
      mockery.mock(AttributeIntrospector.class);
  
  private EvaluatorFactory evaluatorFactory = 
      mockery.mock(EvaluatorFactory.class);
  
  @Test
  public void testWithAnnotatedArrayOfSupportedType() throws Exception {
    final WorkbookIterator iterator = new ColumnIterator(0, 0, null, null, null);
    final Evaluator elementEvaluator = mockery.mock(Evaluator.class);
    final Cell cell = mockery.mock(Cell.class);
    final IterateColumns columns = mockery.mock(IterateColumns.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      atLeast(1).of(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(cell));
      oneOf(introspector).getAnnotation(CellFormat.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(columns));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
      oneOf(cell).value();
      will(returnValue("ref"));
      atLeast(1).of(introspector).getSheetReference();
      will(returnValue("sheetRef"));
      oneOf(evaluatorFactory).createCellEvaluator("sheetRef", "ref", String.class, null);
      will(returnValue(elementEvaluator));
      allowing(introspector).isAbstractType();
      will(returnValue(true));
      allowing(introspector).getType();
      will(returnValue(Object.class));
      oneOf(introspector).getAccessor();
      oneOf(evaluatorFactory).createColumnIterator(columns);
      will(returnValue(iterator));
      oneOf(evaluatorFactory).createArrayEvaluator(String.class, 
          elementEvaluator, iterator);
    } });
    
    Binding binding = ArrayOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, not(nullValue()));
  }

  @Test
  public void testWithNonArrayType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(false));
      allowing(introspector).getSubType();
      will(returnValue(String.class));
    } });
    
    Binding binding = ArrayOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithArrayOfUnsupportedType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(Object.class));
    } });
    
    Binding binding = ArrayOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithNonAnnotatedArrayOfSupportedType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
    } });
    
    Binding binding = ArrayOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithArrayOfSupportedTypeMissingCell() throws Exception {
    final WorkbookIterator iterator = new ColumnIterator(0, 0, null, null, null);
    final IterateColumns columns = mockery.mock(IterateColumns.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(columns));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
      allowing(columns).count();
      will(returnValue(1));
      allowing(columns).increment();
      will(returnValue(1));
      oneOf(evaluatorFactory).createColumnIterator(columns);
      will(returnValue(iterator));
      oneOf(introspector).getSheetReference();
      will(returnValue(null));
    } });
    
    try {
      ArrayOfSimpleTypeBindingStrategy.INSTANCE
          .createBinding(introspector, evaluatorFactory);
      fail("expected WorkbookBindingException");
    }
    catch (WorkbookBindingException ex) {
      mockery.assertIsSatisfied();
    }
  }

  @Test
  public void testWithArrayOfSupportedTypeMissingIteration() throws Exception {
    final Cell cell = mockery.mock(Cell.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(cell));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
    } });
    
    try {
      ArrayOfSimpleTypeBindingStrategy.INSTANCE
          .createBinding(introspector, evaluatorFactory);
      fail("expected WorkbookBindingException");
    }
    catch (WorkbookBindingException ex) {
      mockery.assertIsSatisfied();
    }
  }

}

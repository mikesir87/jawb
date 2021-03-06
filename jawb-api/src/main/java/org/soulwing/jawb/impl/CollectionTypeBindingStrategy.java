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

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;



/**
 * A {@link BindingStrategy} for an attribute with a collection type.
 *
 * @author Carl Harris
 */
class CollectionTypeBindingStrategy implements BindingStrategy {

  public static final CollectionTypeBindingStrategy INSTANCE =
      new CollectionTypeBindingStrategy();
  
  private CollectionTypeBindingStrategy() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Binding createBinding(AttributeIntrospector introspector,
      EvaluatorFactory evaluatorFactory) throws WorkbookBindingException {
    if (!introspector.isCollectionType()) {
      return null;
    }
    
    WorkbookIterator iterator = WorkbookIteratorUtil.createIteratorForAttribute(
        introspector, evaluatorFactory);
    if (iterator == null) {
      return null;
    }
    
    BeanIntrospector boundClass = evaluatorFactory.createBeanIntrospector(
        introspector, introspector.getSubType());

    Evaluator evaluator = evaluatorFactory.createCollectionEvaluator(
        CollectionUtil.getCollectionType(introspector), 
        evaluatorFactory.createBeanEvaluator(boundClass), 
        iterator);
    
    return new BaseBinding(introspector.getAccessor(), evaluator);
  }

}

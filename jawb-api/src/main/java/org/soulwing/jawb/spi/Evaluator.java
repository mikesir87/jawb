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
package org.soulwing.jawb.spi;

import org.soulwing.jawb.WorkbookBindingException;

/**
 * An object that evaluates a portion of a workbook to produce an object.
 *
 * @author Carl Harris
 */
public interface Evaluator {

  /**
   * Evaluates (a portion of) the given workbook to produce an object. 
   * @param workbook
   * @return evaulation result
   * @throws WorkbookBindingException
   */
  Object evaluate(BoundWorkbook workbook) throws WorkbookBindingException;
  
}
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.spark.k8s.operator.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.apache.spark.k8s.operator.listeners.BaseStatusListener;

/** Utility class for loading classes. */
@Slf4j
public final class ClassLoadingUtils {

  private ClassLoadingUtils() {}

  /**
   * load status listener classes for given type of BaseStatusListener
   *
   * @param clazz Class name of status listener. e.g. SparkAppStatusListener
   * @param implementationClassNames Comma-separated implementation class names of given type of
   *     StatusListener
   * @return list of listeners loaded
   * @param <T> Class of status listener. e.g. SparkAppStatusListener
   */
  public static <T extends BaseStatusListener<?, ?>> List<T> getStatusListener(
      Class<T> clazz, String implementationClassNames) {
    List<T> listeners = new ArrayList<>();
    if (StringUtils.isNotBlank(implementationClassNames)) {
      try {
        Set<String> listenerNames = Utils.sanitizeCommaSeparatedStrAsSet(implementationClassNames);
        for (String name : listenerNames) {
          Class<?> listenerClass = Class.forName(name);
          if (clazz.isAssignableFrom(listenerClass)) {
            listeners.add((T) listenerClass.getConstructor().newInstance());
          }
        }
      } catch (ClassNotFoundException
          | NoSuchMethodException
          | InstantiationException
          | IllegalAccessException
          | InvocationTargetException e) {
        if (log.isErrorEnabled()) {
          log.error(
              "Failed to initialize listeners for operator with {}", implementationClassNames, e);
        }
      }
    }
    return listeners;
  }
}

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

package org.apache.spark.k8s.operator.context;

import io.fabric8.kubernetes.client.KubernetesClient;

import org.apache.spark.k8s.operator.BaseResource;

/**
 * Base class for context objects.
 *
 * @param <CR> The type of the custom resource.
 */
public abstract class BaseContext<CR extends BaseResource<?, ?, ?, ?, ?>> {
  public abstract CR getResource();

  public abstract KubernetesClient getClient();
}

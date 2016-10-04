/*
 * Copyright 2016 The Bazel Authors. All rights reserved.
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
package com.google.idea.blaze.java.run;

import com.google.common.collect.ImmutableSet;
import com.google.idea.blaze.base.model.primitives.Kind;
import com.google.idea.blaze.base.run.BlazeCommandRunConfiguration;
import com.google.idea.blaze.base.run.confighandler.BlazeCommandGenericRunConfigurationHandler;
import com.google.idea.blaze.base.run.confighandler.BlazeCommandRunConfigurationHandler;
import com.google.idea.blaze.base.run.confighandler.BlazeCommandRunConfigurationHandlerProvider;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;

/** Java-specific handler for {@link BlazeCommandRunConfiguration}s. */
public class BlazeJavaRunConfigurationHandlerProvider
    implements BlazeCommandRunConfigurationHandlerProvider {

  private static final ImmutableSet<Kind> RELEVANT_RULE_KINDS =
      ImmutableSet.of(Kind.ANDROID_ROBOLECTRIC_TEST, Kind.JAVA_TEST, Kind.JAVA_BINARY);

  static boolean supportsKind(Kind kind) {
    return RELEVANT_RULE_KINDS.contains(kind);
  }

  @Override
  public boolean canHandleKind(Kind kind) {
    return supportsKind(kind);
  }

  @Override
  public BlazeCommandRunConfigurationHandler createHandler(BlazeCommandRunConfiguration config) {
    return new BlazeJavaRunConfigurationHandler(config);
  }

  @Override
  public String getId() {
    return "BlazeJavaRunConfigurationHandlerProvider";
  }

  private static class BlazeJavaRunConfigurationHandler
      extends BlazeCommandGenericRunConfigurationHandler {

    BlazeJavaRunConfigurationHandler(BlazeCommandRunConfiguration configuration) {
      super(configuration);
    }

    private BlazeJavaRunConfigurationHandler(
        BlazeJavaRunConfigurationHandler other, BlazeCommandRunConfiguration configuration) {
      super(other, configuration);
    }

    @Override
    public BlazeJavaRunConfigurationHandler cloneFor(BlazeCommandRunConfiguration configuration) {
      return new BlazeJavaRunConfigurationHandler(this, configuration);
    }

    @Override
    public RunProfileState getState(Executor executor, ExecutionEnvironment environment) {
      return new BlazeJavaRunProfileState(environment, executor instanceof DefaultDebugExecutor);
    }

    @Override
    public String getHandlerName() {
      return "Java Handler";
    }
  }
}

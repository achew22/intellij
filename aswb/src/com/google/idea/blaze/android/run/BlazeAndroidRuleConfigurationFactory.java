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
package com.google.idea.blaze.android.run;

import com.google.idea.blaze.base.ideinfo.RuleIdeInfo;
import com.google.idea.blaze.base.model.primitives.Kind;
import com.google.idea.blaze.base.run.BlazeCommandRunConfiguration;
import com.google.idea.blaze.base.run.BlazeCommandRunConfigurationType;
import com.google.idea.blaze.base.run.BlazeRuleConfigurationFactory;
import com.google.idea.blaze.base.sync.projectview.WorkspaceLanguageSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;

/** Creates run configurations for android_binary and android_test. */
public class BlazeAndroidRuleConfigurationFactory extends BlazeRuleConfigurationFactory {
  @Override
  public boolean handlesRule(
      WorkspaceLanguageSettings workspaceLanguageSettings, RuleIdeInfo rule) {
    return rule.kindIsOneOf(Kind.ANDROID_BINARY, Kind.ANDROID_TEST);
  }

  @Override
  protected ConfigurationFactory getConfigurationFactory() {
    return BlazeCommandRunConfigurationType.getInstance().getFactory();
  }

  @Override
  public void setupConfiguration(RunConfiguration configuration, RuleIdeInfo rule) {
    final BlazeCommandRunConfiguration blazeConfig = (BlazeCommandRunConfiguration) configuration;
    blazeConfig.setTarget(rule.label);
    blazeConfig.setGeneratedName();
  }
}

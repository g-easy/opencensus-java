/*
 * Copyright 2016, Google Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.stats;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link Stats}. */
@RunWith(JUnit4.class)
public final class StatsTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void loadStatsManager_UsesProvidedClassLoader() {
    final RuntimeException toThrow = new RuntimeException("UseClassLoader");
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("UseClassLoader");
    Stats.loadStatsManager(
        new ClassLoader() {
          @Override
          public Class<?> loadClass(String name) {
            throw toThrow;
          }
        });
  }

  @Test
  public void loadStatsManager_IgnoresMissingClasses() {
    assertThat(
            Stats.loadStatsManager(
                    new ClassLoader() {
                      @Override
                      public Class<?> loadClass(String name) throws ClassNotFoundException {
                        throw new ClassNotFoundException();
                      }
                    }))
        .isNull();
  }

  @Test
  public void defaultValues() {
    assertThat(Stats.getStatsContextFactory()).isNull();
    assertThat(Stats.getStatsManager()).isNull();
  }
}

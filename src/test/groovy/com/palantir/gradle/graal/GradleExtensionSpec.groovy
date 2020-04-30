/*
 * (c) Copyright 2018 Palantir Technologies Inc. All rights reserved.
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
 */

package com.palantir.gradle.graal

import nebula.test.ProjectSpec
import org.gradle.api.GradleException

class GradleExtensionSpec extends ProjectSpec {
    GraalExtension extension

    def setup() {
        extension = new GraalExtension(project)
    }

    def 'extension returns the correct Graal download URL and directory name for default Java version and graalVersion 19.2.0'() {
        when:
        extension.graalVersion("19.2.0")

        then:
        extension.getDownloadBaseUrl().get() =~ "https://github.com/oracle/graal/releases/download/"
        extension.getGraalDirectoryName() =~ "graalvm-ce-19.2.0"
    }

    def 'extension returns the correct Graal download URL and directory name for default Java version and graalVersion 19.3.0'() {
        when:
        extension.graalVersion("19.3.0")

        then:
        extension.getDownloadBaseUrl().get() =~ "https://github.com/graalvm/graalvm-ce-builds/releases/download/"
        extension.getGraalDirectoryName() =~ "graalvm-ce-java8-19.3.0"
    }

    def 'extension returns the correct Graal download URL and directory name for Java version 8 and graalVersion 19.2.0'() {
        when:
        extension.javaVersion("8")
        extension.graalVersion("19.2.0")

        then:
        extension.getDownloadBaseUrl().get() =~ "https://github.com/oracle/graal/releases/download/"
        extension.getGraalDirectoryName() =~ "graalvm-ce-19.2.0"
    }

    def 'extension returns the correct Graal download URL and directory name for Java version 8 and graalVersion 19.3.0'() {
        when:
        extension.javaVersion("8")
        extension.graalVersion("19.3.0")

        then:
        extension.getDownloadBaseUrl().get() =~ "https://github.com/graalvm/graalvm-ce-builds/releases/download/"
        extension.getGraalDirectoryName() =~ "graalvm-ce-java8-19.3.0"
    }

    def 'extension should throw exception for graalVersion 19.2.0 and Java version 11'() {
        when:
        extension.javaVersion("11")
        extension.graalVersion("19.2.0")
        extension.getDownloadBaseUrl()

        then:
        thrown GradleException
    }

    def 'extension returns the correct Graal download URL and directory name for Java version 11 and graalVersion 19.3.0'() {
        when:
        extension.javaVersion("11")
        extension.graalVersion("19.3.0")

        then:
        extension.getDownloadBaseUrl().get() =~ "https://github.com/graalvm/graalvm-ce-builds/releases/download/"
        extension.getGraalDirectoryName() =~ "graalvm-ce-java11-19.3.0"
    }

    def 'extension should throw exception for unsupported Java version'() {
        when:
        extension.javaVersion("12")

        then:
        thrown GradleException
    }
}

/*
 *
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.netflix.spinnaker.keel.api.ec2.jackson

import com.netflix.spinnaker.keel.api.InvalidPayload
import com.netflix.spinnaker.keel.api.ec2.image.ArtifactImageProvider
import com.netflix.spinnaker.keel.api.ec2.image.IdImageProvider
import com.netflix.spinnaker.keel.api.ec2.image.ImageProvider
import com.netflix.spinnaker.keel.api.ec2.image.JenkinsImageProvider
import com.netflix.spinnaker.keel.serialization.PropertyNamePolymorphicDeserializer

internal class ImageProviderDeserializer :
  PropertyNamePolymorphicDeserializer<ImageProvider>(ImageProvider::class.java) {
  override fun identifySubType(fieldNames: Collection<String>): Class<out ImageProvider> =
    when {
      "imageId" in fieldNames -> IdImageProvider::class.java
      "deliveryArtifact" in fieldNames -> ArtifactImageProvider::class.java
      "buildHost" in fieldNames -> JenkinsImageProvider::class.java
      else -> throw InvalidPayload("ImageProvider")
    }
}

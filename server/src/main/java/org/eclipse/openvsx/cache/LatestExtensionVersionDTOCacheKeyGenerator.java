/** ******************************************************************************
 * Copyright (c) 2022 Precies. Software Ltd and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * ****************************************************************************** */
package org.eclipse.openvsx.cache;

import org.eclipse.openvsx.dto.ExtensionVersionDTO;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class LatestExtensionVersionDTOCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if(params[0] instanceof List<?>) {
            var versions = (List<?>) params[0];
            if(!versions.isEmpty() && versions.get(0) instanceof ExtensionVersionDTO) {
                var extVersion = (ExtensionVersionDTO) versions.get(0);
                return generate(target, method, extVersion.getExtensionId(), extVersion.getType());
            } else {
                return null;
            }
        } else {
            var extensionId = (long) params[0];
            var type = (ExtensionVersionDTO.Type) params[1];
            return "extension=" + extensionId + ",type=" + type;
        }
    }
}

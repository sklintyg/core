# Customer Terminate Service (CTS)

## Running CTS locally

You can either run CTS locally inside IntelliJ (using Run-configurations) or by using the included Gradle wrapper.

When starting CTS locally for development and testing, you want to make sure that the following
Spring profiles are active: `testability,dev`

They are activated automatically when running Gradle (`./gradlew bootRun`), but in IntelliJ you have to add the Spring
profiles to your Run-configuration (`Active profiles`) before starting the service.

## Swagger Api Documentation

Local URL: http://localhost:18010/swagger-ui/index.html
URL: https://cts.localtest.me/swagger-ui/index.html

## Licens

Copyright (C) 2021 Inera AB (http://www.inera.se)

Terminate Service is free software: you can redistribute it and/or modify it under the terms of the
GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

Terminate Service is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details.

Se även [LICENSE.md](LICENSE.md). 
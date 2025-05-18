Set-Location -Path "$PSScriptRoot"

mvn clean

mvn install -Pdev -DskipTests

Set-Location -Path "docs-web"

mvn jetty:run -Pdev -DskipTests

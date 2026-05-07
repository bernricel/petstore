$root = Split-Path -Parent $PSScriptRoot
& "$root\mvnw.cmd" -f "$PSScriptRoot\pom.xml" test


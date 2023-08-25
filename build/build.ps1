# Stop the script if any command fails
$ErrorActionPreference = "Stop"

Write-Host "build.ps1 running..."
Write-Host $PWD
Write-Host (Get-ChildItem)

$mvnwPath = "$BUILD_SVR_PATH\$env:CI_COMMIT_REF_NAME\mvnw"

# Run the Maven Wrapper command
& $mvnwPath clean package -DskipTests -Pexclude-properties

# Copy the application.properties file into the target directory
Copy-Item -Path "src\main\resources\application.properties" -Destination "config\application.properties"

Write-Host "Build and copy operations completed!"
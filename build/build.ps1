# Stop the script if any command fails
$ErrorActionPreference = "Stop"

Write-Host "build.ps1 running..."
Write-Host $PWD
Write-Host (Get-ChildItem)

$mvnwPath = "$BUILD_SVR_PATH\$env:CI_COMMIT_REF_NAME\mvnw"

# Run the Maven Wrapper command
cmd /c $mvnwPath clean package -DskipTests -Pexclude-properties

docker build -t "$env:CI_COMMIT_REF_NAME" .
if (Test-Path "bin\$env:CI_COMMIT_REF_NAME.tar") {
    Remove-Item -Path "bin\$env:CI_COMMIT_REF_NAME.tar" -Recurse -Force;
}
New-Item -Path "$BUILD_SVR_PATH\$env:CI_COMMIT_REF_NAME\bin" -ItemType Directory -Force;

docker save -o ".\bin\$env:CI_COMMIT_REF_NAME.tar" "$env:CI_COMMIT_REF_NAME"

# Copy the application.properties file into the target directory
Copy-Item -Path "$BUILD_SVR_PATH\$env:CI_COMMIT_REF_NAME\src\main\resources\application.properties" -Destination "$BUILD_SVR_PATH\$env:CI_COMMIT_REF_NAME\config\application.properties"

Write-Host "Build and copy operations completed!"
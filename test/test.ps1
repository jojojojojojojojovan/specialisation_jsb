$expectedOutput = @{
    "test1.ps1" = $true;
    "test2.ps1" = $true;
}

$testCasesPath = "$PSScriptRoot/test_cases/"

Get-ChildItem -Path $testCasesPath -Filter *.ps1 | ForEach-Object {
    $scriptName = $_.Name
    Write-Host "Running $scriptName"

    $output = & "$testCasesPath\$scriptname"

    if($output.success -eq $expectedOutput[$scriptName]) {
        Write-Host "$scriptName PASSED" -ForegroundColor Green
    }
    else {
        Write-Host "$scriptName FAILED. Expected: $expectedOutput[$scriptName] but got $ouput" -ForegroundColor Red
    }
}

Write-Host "Tests completed!"
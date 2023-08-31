$expectedOutput = @{
    "test1_createAcc.ps1" = $false;
    "test1_updateAcc_pass.ps1" = $true;
    "test1_updateAcc_fail.ps1"=$false
    "test2_getApp_fail.ps1" = $false;
    "test3__createAccGroup_pass.ps1" = $true;
    "test3__createAccGroup_fail.ps1" = $false;
    "test4__createApp_pass.ps1" = $true;
}

$testCasesPath = "$PSScriptRoot\test_cases\"

Get-ChildItem -Path $testCasesPath -Filter *.ps1 | ForEach-Object {
    $scriptName = $_.Name
    Write-Host "Running $scriptName"

    $output = & "$testCasesPath\$scriptname"

    Write-Host $output

    if($output.success -eq $expectedOutput[$scriptName]) {
        Write-Host "$scriptName PASSED" -ForegroundColor Green
    }
    else {
        Write-Host "$scriptName FAILED. Expected: $expectedOutput[$scriptName] but got $output" -ForegroundColor Red
    }
}

Write-Host "Tests completed!!"
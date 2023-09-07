# Define endpoint, headers, and payload
$apiEndpoint = "http://localhost:8080/getApplication"

$headers = @{
    'Content-Type'  = 'application/json'
}
$body = @{
    'appAcronym' = 'App'
} | ConvertTo-Json -Depth 10

# Make the API call
$response = Invoke-RestMethod -Uri $apiEndpoint -Method Post -Headers $headers -Body $body 

# Optionally print the response
Write-Host $response
return $response

# ... continue with any additional logic or tests ...

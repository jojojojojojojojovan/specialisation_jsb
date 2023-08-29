# Set the URI
$uri = "http://localhost:8080/createAccGroup"


# Create headers
$headers = @{
    'Content-Type' = 'application/json';
}

# Create the request body
$body = @{
        un = "admin"
        gn = "admin"
        groupName = "admin"
} | ConvertTo-Json -Depth 10

# Make the REST API call
$response = Invoke-RestMethod -Method Post -Uri $uri -Headers $headers -Body $body

# Optionally print the response
Write-Output $response
return $response
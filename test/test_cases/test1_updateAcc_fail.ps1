# Set the URI
$uri = "http://localhost:8080/api/accounts/updateAccount"

# Create headers
$headers = @{
    'Content-Type' = 'application/json'
}

# Create the request body
$body = @{
    username = "admin"
    newPassword = "abc123!!"
    verifyPassword = "p@ssw0rd"
    email = "testtest.com"
    
} | ConvertTo-Json -Depth 10

# Make the REST API call
$response = Invoke-RestMethod -Method Put -Uri $uri -Headers $headers -Body $body

# Optionally print the response
Write-Output $response

# ... continue with any additional logic or tests ...

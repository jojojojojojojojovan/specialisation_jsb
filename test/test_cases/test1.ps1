# Set the URI
$uri = "http://localhost:8080/api/accounts/create"

# Create headers
$headers = @{
    'Content-Type' = 'application/json'
}

# Create the request body
$body = @{
    account = @{
        username = "admin1"
        password = "p@ssword1"
        status   = 1
        groups   = @(
            @{ groupName = "admin" },
            @{ groupName = "project leader" }
        )
    }
} | ConvertTo-Json -Depth 10

# Make the REST API call
$response = Invoke-RestMethod -Method Post -Uri $uri -Headers $headers -Body $body

# Optionally print the response
Write-Output $response

# ... continue with any additional logic or tests ...

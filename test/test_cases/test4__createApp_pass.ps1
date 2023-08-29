# Set the URI
$uri = "http://localhost:8080/createApplication"


# Create headers
$headers = @{
    'Content-Type' = 'application/json'
}

# Create the request body
$body = @{
        un = "pl"
        gn = "project leader"
        acronym = "TMS2"
        app_Description = "TMS2"
        rnumber = 2
        endDate = "2023-08-31"
        startDate = "2023-08-31"
} | ConvertTo-Json -Depth 10

# Make the REST API call
$response = Invoke-RestMethod -Method Post -Uri $uri -Headers $headers -Body $body

# Optionally print the response
Write-Output $response
return $response
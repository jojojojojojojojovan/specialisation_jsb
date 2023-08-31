# Set the URI
$uri = "http://localhost:8080/api/accounts/create"

# Create headers for content type
$headers = @{
    'Content-Type' = 'application/json'
}

# Create the request body
$body = @{
    account = @{
        username = "admin8"
        password = "p@ssword1"
        status   = 1
        groups   = @(
            @{ groupName = "admin" }
        )
    }
}

# Convert the body to JSONgit
$jsonBody = $body | ConvertTo-Json -Depth 10

# Setting up the Cookie as a Web Session
$webSession = New-Object Microsoft.PowerShell.Commands.WebRequestSession
$authTokenValue = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlwIjoiMDowOjA6MDowOjA6MDoxIiwidXNlckFnZW50IjoiUG9zdG1hblJ1bnRpbWUvNy4zMi4zIiwiZXhwIjoxNjkzNTA4NDgyfQ.LycVAn0AfuZQoWMHR436BVngPVP3HqCbkq8o1iTB7uLJEFXXzdEFRjgYeBlzELrFjzVytWpK6Ztvva1EkEt2Hw"
$webSession.Cookies.Add((New-Object System.Net.Cookie("authToken", $authTokenValue, "/", "localhost")))

# Make the REST API call using Invoke-RestMethod
$response = Invoke-RestMethod -Method Post -Uri $uri -Headers $headers -Body $jsonBody -WebSession $webSession

# Display the response
return $response





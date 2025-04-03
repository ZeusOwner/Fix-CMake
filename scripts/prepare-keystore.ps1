# PowerShell script to prepare the keystore for GitHub Actions
# This script helps encode the keystore file to base64 for GitHub secrets

param(
    [Parameter(Mandatory=$true)]
    [string]$KeystorePath,
    
    [Parameter(Mandatory=$false)]
    [string]$KeyAlias = "bearmod",
    
    [Parameter(Mandatory=$false)]
    [string]$KeystorePassword = "bearmod123",
    
    [Parameter(Mandatory=$false)]
    [string]$KeyPassword = "bearmod123"
)

# Check if the keystore file exists
if (-not (Test-Path $KeystorePath)) {
    Write-Error "Keystore file not found at: $KeystorePath"
    exit 1
}

# Create output directory if it doesn't exist
$outputDir = "keystore"
if (-not (Test-Path $outputDir)) {
    New-Item -ItemType Directory -Path $outputDir | Out-Null
    Write-Host "Created directory: $outputDir"
}

# Get the keystore filename
$keystoreFileName = Split-Path $KeystorePath -Leaf
$outputBase64File = Join-Path $outputDir "$keystoreFileName.base64"

# Encode the keystore file to base64
$keystoreBytes = [System.IO.File]::ReadAllBytes($KeystorePath)
$base64String = [System.Convert]::ToBase64String($keystoreBytes)
[System.IO.File]::WriteAllText($outputBase64File, $base64String)

Write-Host "Keystore encoded to base64 and saved to: $outputBase64File"
Write-Host ""
Write-Host "To set up GitHub secrets for signing:"
Write-Host "1. Copy the contents of the $outputBase64File file"
Write-Host "2. Add the following secrets in your GitHub repository:"
Write-Host "   SIGNING_KEY: [contents of the base64 file]"
Write-Host "   KEY_ALIAS: $KeyAlias"
Write-Host "   KEY_STORE_PASSWORD: $KeystorePassword"
Write-Host "   KEY_PASSWORD: $KeyPassword"
Write-Host ""
Write-Host "These secrets will be used by the GitHub Actions workflow to sign your app."

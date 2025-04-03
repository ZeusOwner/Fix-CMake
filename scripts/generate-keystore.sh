#!/bin/bash

# Script para generar un keystore para firmar la aplicación Android
# Este script es solo para desarrollo y pruebas
# Para producción, debe crear un keystore seguro y guardarlo de forma segura

# Configuración
KEYSTORE_FILE="bearmod.keystore"
KEYSTORE_PASSWORD="bearmod123"
KEY_ALIAS="bearmod"
KEY_PASSWORD="bearmod123"
VALIDITY_DAYS=10000

# Crear directorio si no existe
mkdir -p keystore

# Generar el keystore
keytool -genkeypair \
  -alias $KEY_ALIAS \
  -keyalg RSA \
  -keysize 2048 \
  -validity $VALIDITY_DAYS \
  -keystore keystore/$KEYSTORE_FILE \
  -storepass $KEYSTORE_PASSWORD \
  -keypass $KEY_PASSWORD \
  -dname "CN=BearMod, OU=Development, O=BearMod, L=City, ST=State, C=US"

echo "Keystore generado en keystore/$KEYSTORE_FILE"
echo "Contraseña del keystore: $KEYSTORE_PASSWORD"
echo "Alias de la clave: $KEY_ALIAS"
echo "Contraseña de la clave: $KEY_PASSWORD"

# Instrucciones para configurar los secretos en GitHub
echo ""
echo "Para configurar los secretos en GitHub:"
echo "1. Codifica el keystore en Base64:"
echo "   base64 -i keystore/$KEYSTORE_FILE | tr -d '\n' > keystore/$KEYSTORE_FILE.base64"
echo ""
echo "2. Copia el contenido del archivo keystore/$KEYSTORE_FILE.base64"
echo ""
echo "3. Agrega los siguientes secretos en GitHub:"
echo "   SIGNING_KEY: [contenido del archivo base64]"
echo "   KEY_ALIAS: $KEY_ALIAS"
echo "   KEY_STORE_PASSWORD: $KEYSTORE_PASSWORD"
echo "   KEY_PASSWORD: $KEY_PASSWORD"

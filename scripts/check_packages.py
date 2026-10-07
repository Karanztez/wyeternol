import urllib.request
import json

url = "https://api.github.com/users/Karanztez/packages?package_type=maven"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
try:
    with urllib.request.urlopen(req) as resp:
        data = json.loads(resp.read().decode())
        print(f"Total packages: {len(data)}")
        for p in data:
            print(f"Name: {p.get('name')}, Type: {p.get('package_type')}, URL: {p.get('html_url')}")
except Exception as e:
    print(f"Error: {e}")

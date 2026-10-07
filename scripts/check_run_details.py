import urllib.request
import json
import time

url = "https://api.github.com/repos/Karanztez/wyeternol/actions/runs/37629700346"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
try:
    with urllib.request.urlopen(req) as resp:
        r = json.loads(resp.read().decode())
        print(f"Status: {r.get('status')}")
        print(f"Conclusion: {r.get('conclusion')}")
        print(f"HTML URL: {r.get('html_url')}")
except Exception as e:
    print(f"Error: {e}")

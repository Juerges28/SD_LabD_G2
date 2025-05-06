import socket
import threading
import pickle

class ChatMessage:
    WHOISIN = 0
    MESSAGE = 1
    LOGOUT = 2

    def __init__(self, type, message):
        self.type = type
        self.message = message

class Client:
    def __init__(self, server, port, username):
        self.server = server
        self.port = port
        self.username = username
        self.sock = None

    def start(self):
        try:
            self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.sock.connect((self.server, self.port))
        except Exception as e:
            print(f"Error connecting to server: {e}")
            return False

        print(f"Connected to {self.server}:{self.port}")
        
        try:
            # Send username first
            self.sock.sendall(pickle.dumps(self.username))
        except Exception as e:
            print(f"Login failed: {e}")
            self.disconnect()
            return False

        threading.Thread(target=self.listen_from_server, daemon=True).start()
        return True

    def send_message(self, msg: ChatMessage):
        try:
            self.sock.sendall(pickle.dumps(msg))
        except Exception as e:
            print(f"Error sending message: {e}")

    def disconnect(self):
        try:
            self.sock.close()
        except:
            pass

    def listen_from_server(self):
        while True:
            try:
                data = self.sock.recv(4096)
                if not data:
                    break
                msg = pickle.loads(data)
                print(msg)
                print("> ", end='', flush=True)
            except Exception as e:
                print(f"\n*** Connection closed by server: {e} ***")
                break

def main():
    import sys

    server = "localhost"
    port = 1500
    username = "Anonymous"

    if len(sys.argv) >= 2:
        username = sys.argv[1]
    if len(sys.argv) >= 3:
        port = int(sys.argv[2])
    if len(sys.argv) >= 4:
        server = sys.argv[3]

    username = input("Enter the username: ") or username

    client = Client(server, port, username)
    if not client.start():
        return

    print("\nHello! Welcome to the chatroom.")
    print("Instructions:")
    print("1. Type message to broadcast.")
    print("2. Type '@username yourmessage' to send private message.")
    print("3. Type 'WHOISIN' to see active users.")
    print("4. Type 'LOGOUT' to disconnect.")

    while True:
        msg = input("> ")
        if msg.upper() == "LOGOUT":
            client.send_message(ChatMessage(ChatMessage.LOGOUT, ""))
            break
        elif msg.upper() == "WHOISIN":
            client.send_message(ChatMessage(ChatMessage.WHOISIN, ""))
        else:
            client.send_message(ChatMessage(ChatMessage.MESSAGE, msg))

    client.disconnect()

if __name__ == "__main__":
    main()

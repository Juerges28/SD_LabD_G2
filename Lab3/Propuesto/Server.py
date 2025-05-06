import socket
import threading
import pickle
from datetime import datetime

# Message type constants (equivalent to ChatMessage types)
MESSAGE = 1
LOGOUT = 2
WHOISIN = 3

# Message class to mimic Java's ChatMessage
class ChatMessage:
    def __init__(self, msg_type, message):
        self.type = msg_type
        self.message = message

class ClientThread(threading.Thread):
    def __init__(self, server, conn, address):
        threading.Thread.__init__(self)
        self.server = server
        self.conn = conn
        self.address = address
        self.username = None
        self.running = True
        self.date = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    def run(self):
        try:
            # Receive the username first
            self.username = pickle.loads(self.conn.recv(4096)).strip()
            self.server.broadcast(f" *** {self.username} has joined the chat room. ***")
        except Exception as e:
            print(f"Error reading username: {e}")
            self.running = False

        while self.running:
            try:
                data = self.conn.recv(4096)
                if not data:
                    break
                cm = pickle.loads(data)
                if cm.type == MESSAGE:
                    msg = f"{self.username}: {cm.message}"
                    if not self.server.broadcast(msg):
                        self.send_message(" *** Sorry. No such user exists. ***")
                elif cm.type == LOGOUT:
                    print(f"{self.username} disconnected with a LOGOUT message.")
                    self.running = False
                elif cm.type == WHOISIN:
                    user_list = f"List of the users connected at {self.server.get_time()}\n"
                    for idx, ct in enumerate(self.server.clients):
                        user_list += f"{idx + 1}) {ct.username} since {ct.date}\n"
                    self.send_message(user_list)
            except Exception as e:
                print(f"{self.username} Exception reading Streams: {e}")
                break

        self.server.remove_client(self)
        self.close()

    def send_message(self, message):
        try:
            self.conn.sendall(pickle.dumps(message))
            return True
        except Exception:
            print(f"Error sending message to {self.username}")
            return False

    def close(self):
        try:
            self.conn.close()
        except:
            pass

class Server:
    def __init__(self, port=1500):
        self.port = port
        self.clients = []
        self.keep_going = True

    def start(self):
        print(f"Server waiting for Clients on port {self.port}.")
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind(('', self.port))
        server_socket.listen()

        try:
            while self.keep_going:
                conn, addr = server_socket.accept()
                if not self.keep_going:
                    break
                ct = ClientThread(self, conn, addr)
                self.clients.append(ct)
                ct.start()
        except KeyboardInterrupt:
            print("Server shutting down.")
        finally:
            server_socket.close()
            for ct in self.clients:
                ct.running = False
                ct.close()

    def get_time(self):
        return datetime.now().strftime("%H:%M:%S")

    def broadcast(self, message):
        print(f"{self.get_time()} {message}")
        is_private = False
        parts = message.split(" ", 2)
        if len(parts) >= 2 and parts[1].startswith("@"):
            is_private = True
            to_user = parts[1][1:]
            content = parts[0] + parts[2] if len(parts) > 2 else ""
            for ct in self.clients:
                if ct.username == to_user:
                    return ct.send_message(f"{self.get_time()} {content}")
            return False
        else:
            for ct in self.clients[:]:
                if not ct.send_message(f"{self.get_time()} {message}"):
                    self.clients.remove(ct)
        return True

    def remove_client(self, client):
        if client in self.clients:
            self.clients.remove(client)
            self.broadcast(f" *** {client.username} has left the chat room. ***")

if __name__ == "__main__":
    import sys
    port = 1500
    if len(sys.argv) > 1:
        try:
            port = int(sys.argv[1])
        except ValueError:
            print("Invalid port number.")
            sys.exit(1)
    server = Server(port)
    server.start()

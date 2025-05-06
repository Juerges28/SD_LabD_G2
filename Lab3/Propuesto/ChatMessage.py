import pickle

class ChatMessage:
    # Tipos de mensajes
    WHOISIN = 0
    MESSAGE = 1
    LOGOUT = 2

    def __init__(self, msg_type, message):
        self.type = msg_type
        self.message = message

    def get_type(self):
        return self.type

    def get_message(self):
        return self.message

    # Métodos de serialización y deserialización
    def serialize(self):
        return pickle.dumps(self)

    @staticmethod
    def deserialize(data):
        return pickle.loads(data)

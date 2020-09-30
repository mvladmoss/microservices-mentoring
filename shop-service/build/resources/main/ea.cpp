#pragma comment(lib, "ws2_32.lib")

#include <WinSock2.h>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>;


int main()
{
WORD wVersionRequested;
WSADATA wsaData;
wVersionRequested = MAKEWORD(2, 2);
WSAStartup(wVersionRequested, &wsaData);
SOCKET s = socket(AF_INET, SOCK_STREAM, 0);
//коробка
sockaddr_in local;
local.sin_family = AF_INET; //семейство протоколов
local.sin_port = htons(1280);
local.sin_addr.s_addr = htonl(INADDR_ANY);// принимаемый ip-адрес

int c = bind(s, (sockaddr*)&local, sizeof(local));
int r = listen(s, 5);
while (true)
{
const int len_b = 256, len_buf = 256;
char buf[len_buf], b[len_b], * Res;
//структура определяет удаленный адрес,
//с которым соединяется сокет
sockaddr_in remote_addr;
int size = sizeof(remote_addr);
SOCKET s2 = accept(s, (sockaddr*)&remote_addr, &size);
while (recv(s2, b, sizeof(b), 0) != 0)
{
int i = 0;
int k = 0;
while (b[i] == ' ') i++; // пропускаем пробелы в начале
b[k++] = b[i++]; // переписали 1-ый символ
for (; b[i]; i++)
{
if (b[i] != ' ' || b[i - 1] != ' ')
{
b[k++] = b[i];
}
}
b[k] = '\0'; // конец строки
const int len_res = strlen(b) + 1;
Res = new char[len_res];
strcpy_s(Res, len_res, b);
Res[strlen(Res)] = '\0';
//Посылает данные на соединенный сокет
send(s2, Res, len_res, 0);
}
closesocket(s2);
}
WSACleanup();

}
#!/usr/bin/env python
# coding: utf-8

# In[ ]:


import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email import encoders
from email.mime.image import MIMEImage
import cv2
from matplotlib import pyplot as plt
import sys
import os

# Java에서 전달된 인자를 받음
if len(sys.argv) > 1:
    mail = sys.argv[1]  # Java에서 전달한 첫 번째 인자를 받음
    print(f"Received variable from Java: {mail}")

# 메일 관련 변수 설정
smtp_server = "smtp.gmail.com"
smtp_port = 587
sender_email = "qmfkzkf123@gmail.com"
receiver_email = "bow10004@naver.com"
password = "ygjz unmj uefc bpah"  # 앱 비밀번호 사용

# 이메일 메시지 설정
subject = "아오기백시치"
body = "집에 보내다오"

# MIME 형식의 이메일 작성
msg = MIMEMultipart()
msg["From"] = sender_email
msg["To"] = receiver_email
msg["Subject"] = subject

# 이메일 본문 추가
msg.attach(MIMEText(body, "plain"))

# 이미지 파일 경로 설정
image_path = "C:/Users/smhrd/Desktop/짬통/doldol.png"  # 첨부할 이미지 파일 경로

# 이미지 파일이 존재하는 경우에만 첨부
if os.path.exists(image_path):
    with open(image_path, "rb") as image_file:
        # MIMEImage로 이미지 파일을 읽어서 변환
        img = MIMEImage(image_file.read())
    
    # 첨부 파일 이름 설정
    img.add_header("Content-Disposition", f"attachment; filename= {os.path.basename(image_path)}")
    msg.attach(img)

# 이메일 전송
try:
    # SMTP 서버에 연결 (TLS)
    server = smtplib.SMTP(smtp_server, smtp_port)
    server.starttls()  # TLS 사용 시작
    server.login(sender_email, password)  # 로그인
    server.sendmail(sender_email, receiver_email, msg.as_string())  # 이메일 전송
    print("이메일이 성공적으로 전송되었습니다.")
except Exception as e:
    print(f"이메일 전송에 실패했습니다: {e}")
finally:
    server.quit()  # 서버 연결 종료

# 이미지 파일 열기 (이미지 파일이 존재할 경우에만)
if os.path.exists(image_path):
    img = cv2.imread(image_path, cv2.IMREAD_COLOR)


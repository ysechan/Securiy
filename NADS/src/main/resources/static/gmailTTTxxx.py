#!/usr/bin/env python
# coding: utf-8
# -*- coding: utf-8 -*-

import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
import sys
import os
from datetime import datetime

# Java에서 전달된 인자를 받음
if len(sys.argv) > 1:
    mail = sys.argv[1]  # Java에서 전달한 첫 번째 인자를 받음
    print(f"Received variable from Java: {mail}")

# 시간 관련 변수 설정
now = datetime.now()
formatted_date = now.strftime("[%Y-%m-%d %H]")

# 메일 관련 변수 설정
smtp_server = "smtp.gmail.com"
smtp_port = 587
sender_email = "qmfkzkf123@gmail.com"
receiver_email = mail
password = "ygjz unmj uefc bpah"  # 앱 비밀번호 사용

# 이메일 메시지 설정
subject = "[긴급] 트래픽 세션 이상 탐지 알림"
body = f"""
안녕하세요,

트래픽 세션 모니터링 시스템에서 이상 패턴이 감지되었습니다. 해당 이슈가 시스템 운영에 영향을 미칠 가능성이 있으니 확인을 부탁드립니다.

발생 시각: {formatted_date}
이상 탐지 유형: [예: 급격한 트래픽 증가, 비정상 세션 지속 등]

문제가 지속되거나 추가적인 조치가 필요한 경우 빠른 대처를 권장드립니다. 관리 콘솔에서 상세 정보를 확인해 주시고, 필요시 기술팀에 바로 문의 바랍니다.

감사합니다.
[시스템 모니터링 팀]
"""

# MIME 형식의 이메일 작성
msg = MIMEMultipart()
msg["From"] = sender_email
msg["To"] = receiver_email
msg["Subject"] = subject

# 이메일 본문 추가
msg.attach(MIMEText(body, "plain"))

# 이미지 파일 경로 설정
image_path = ""  # 첨부할 이미지 파일 경로

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

import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handleRegister, reset } from './register.reducer';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const handleValidSubmit = ({ username, email, firstPassword }) => {
    dispatch(handleRegister({ login: username, email, password: firstPassword, langKey: 'en' }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1 id="register-title" data-cy="registerTitle">
            Kayıt
          </h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="username"
              label="Kullanıcı Adı"
              placeholder="Kullanıcı Adınız"
              validate={{
                required: { value: true, message: 'Kullanıcı adı zorunlu.' },
                pattern: {
                  value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                  message: 'Your username is invalid.',
                },
                minLength: { value: 1, message: 'Kullanıcı adı en az 1 karakter olmak zorundadır' },
                maxLength: { value: 50, message: 'Kullanıcı adı en fazla 50 karakter olabilir' },
              }}
              data-cy="username"
            />
            <ValidatedField
              name="email"
              label="E-posta"
              placeholder="E-posta adresiniz"
              type="email"
              validate={{
                required: { value: true, message: 'E-posta adresiniz zorunludur.' },
                minLength: { value: 5, message: 'E-posta adresiniz en az 5 karakter olmalıdır' },
                maxLength: { value: 254, message: 'E-posta adresiniz 50 karakterden fazla olamaz' },
                validate: v => isEmail(v) || 'E-posta adresiniz uygun değil.',
              }}
              data-cy="email"
            />
            <ValidatedField
              name="firstPassword"
              label="Yeni Şifre"
              placeholder="Yeni Şifreniz"
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, message: 'Şifre zorunlu alandır.' },
                minLength: { value: 4, message: 'Şifreniz en az 4 karakter olmak zorundadır' },
                maxLength: { value: 50, message: 'Şifreniz 50 karakterden uzun olamaz' },
              }}
              data-cy="firstPassword"
            />
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="secondPassword"
              label="Yeni Şifre Doğrulama"
              placeholder="Yeni Şifrenizi Doğrulayınız"
              type="password"
              validate={{
                required: { value: true, message: 'Doğrulama şifresi zorunludur.' },
                minLength: { value: 4, message: 'Doğrulama şifreniz en az 4 karakter olmalıdır' },
                maxLength: { value: 50, message: 'Doğrulama şifreniz 50 karakterden uzun olamaz' },
                validate: v => v === password || 'Şifreler eşleşmedi!',
              }}
              data-cy="secondPassword"
            />
            <Button id="register-submit" color="primary" type="submit" data-cy="submit">
              Kayıt Ol
            </Button>
          </ValidatedForm>
          <p>&nbsp;</p>
          <Alert color="warning">
            <span> </span>
            <Link to="/login" className="alert-link">
              Giriş
            </Link>
            <span>
              yapmak istiyorsanız, varsayılan hesaplardan birini deneyebilirsiniz:
              <br />- Yönetici (kullanıcı adı=&quot;admin&quot; ve şifre=&quot;admin&quot;) <br />- Kullanıcı (kullanıcı
              adı=&quot;user&quot; and şifre=&quot;user&quot;).
            </span>
          </Alert>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;

import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Row, Col, Button } from 'reactstrap';
import { toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { savePassword, reset } from './password.reducer';

export const PasswordPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(reset());
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  const handleValidSubmit = ({ currentPassword, newPassword }) => {
    dispatch(savePassword({ currentPassword, newPassword }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const account = useAppSelector(state => state.authentication.account);
  const successMessage = useAppSelector(state => state.password.successMessage);
  const errorMessage = useAppSelector(state => state.password.errorMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    } else if (errorMessage) {
      toast.error(errorMessage);
    }
    dispatch(reset());
  }, [successMessage, errorMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="password-title">
            [<strong>{account.login}</strong>] kullanıcısı için şifre
          </h2>
          <ValidatedForm id="password-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="currentPassword"
              label="Mevcut Şifre"
              placeholder="Mevcut Şifreniz"
              type="password"
              validate={{
                required: { value: true, message: 'Şifre zorunlu alandır.' },
              }}
              data-cy="currentPassword"
            />
            <ValidatedField
              name="newPassword"
              label="Yeni Şifre"
              placeholder="Yeni Şifreniz"
              type="password"
              validate={{
                required: { value: true, message: 'Şifre zorunlu alandır.' },
                minLength: { value: 4, message: 'Şifreniz en az 4 karakter olmak zorundadır' },
                maxLength: { value: 50, message: 'Şifreniz 50 karakterden uzun olamaz' },
              }}
              onChange={updatePassword}
              data-cy="newPassword"
            />
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="confirmPassword"
              label="Yeni Şifre Doğrulama"
              placeholder="Yeni Şifrenizi Doğrulayınız"
              type="password"
              validate={{
                required: { value: true, message: 'Doğrulama şifresi zorunludur.' },
                minLength: { value: 4, message: 'Doğrulama şifreniz en az 4 karakter olmalıdır' },
                maxLength: { value: 50, message: 'Doğrulama şifreniz 50 karakterden uzun olamaz' },
                validate: v => v === password || 'Şifreler eşleşmedi!',
              }}
              data-cy="confirmPassword"
            />
            <Button color="success" type="submit" data-cy="submit">
              Kaydet
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordPage;

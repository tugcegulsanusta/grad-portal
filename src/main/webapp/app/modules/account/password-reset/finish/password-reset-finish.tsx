import React, { useState, useEffect } from 'react';
import { Col, Row, Button } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handlePasswordResetFinish, reset } from '../password-reset.reducer';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PasswordResetFinishPage = () => {
  const dispatch = useAppDispatch();

  const [searchParams] = useSearchParams();
  const key = searchParams.get('key');

  const [password, setPassword] = useState('');

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const handleValidSubmit = ({ newPassword }) => dispatch(handlePasswordResetFinish({ key, newPassword }));

  const updatePassword = event => setPassword(event.target.value);

  const getResetForm = () => {
    return (
      <ValidatedForm onSubmit={handleValidSubmit}>
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
          data-cy="resetPassword"
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
          data-cy="confirmResetPassword"
        />
        <Button color="success" type="submit" data-cy="submit">
          Yeni şifreyi doğrula
        </Button>
      </ValidatedForm>
    );
  };

  const successMessage = useAppSelector(state => state.passwordReset.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="4">
          <h1>Şifreyi sıfırla</h1>
          <div>{key ? getResetForm() : null}</div>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordResetFinishPage;

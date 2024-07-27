import React, { useEffect } from 'react';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from './settings.reducer';

export const SettingsPage = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const successMessage = useAppSelector(state => state.settings.successMessage);

  useEffect(() => {
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  const handleValidSubmit = values => {
    dispatch(
      saveAccountSettings({
        ...account,
        ...values,
      }),
    );
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="settings-title">
            Kullanıcı ayarları [<strong>{account.login}</strong>]
          </h2>
          <ValidatedForm id="settings-form" onSubmit={handleValidSubmit} defaultValues={account}>
            <ValidatedField
              name="firstName"
              label="İsim"
              id="firstName"
              placeholder="Adınız"
              validate={{
                required: { value: true, message: 'İsim zorunludur.' },
                minLength: { value: 1, message: 'İsim en az 1 karakter olmak zorundadır' },
                maxLength: { value: 50, message: 'İsim en fazla 50 karakter olabilir' },
              }}
              data-cy="firstname"
            />
            <ValidatedField
              name="lastName"
              label="Soyisim"
              id="lastName"
              placeholder="Soyadınız"
              validate={{
                required: { value: true, message: 'Soyisim zorunludur.' },
                minLength: { value: 1, message: 'Soyisim en az 1 karakter olmak zorundadır' },
                maxLength: { value: 50, message: 'Soyisim en fazla 50 karakter olabilir' },
              }}
              data-cy="lastname"
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
            <Button color="primary" type="submit" data-cy="submit">
              Kaydet
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default SettingsPage;

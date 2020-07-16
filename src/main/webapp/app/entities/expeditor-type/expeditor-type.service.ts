import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExpeditorType } from 'app/shared/model/expeditor-type.model';

type EntityResponseType = HttpResponse<IExpeditorType>;
type EntityArrayResponseType = HttpResponse<IExpeditorType[]>;

@Injectable({ providedIn: 'root' })
export class ExpeditorTypeService {
  public resourceUrl = SERVER_API_URL + 'api/expeditor-types';

  constructor(protected http: HttpClient) {}

  create(expeditorType: IExpeditorType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditorType);
    return this.http
      .post<IExpeditorType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expeditorType: IExpeditorType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditorType);
    return this.http
      .put<IExpeditorType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpeditorType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpeditorType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expeditorType: IExpeditorType): IExpeditorType {
    const copy: IExpeditorType = Object.assign({}, expeditorType, {
      createdAt: expeditorType.createdAt && expeditorType.createdAt.isValid() ? expeditorType.createdAt.toJSON() : undefined,
      updatedAt: expeditorType.updatedAt && expeditorType.updatedAt.isValid() ? expeditorType.updatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((expeditorType: IExpeditorType) => {
        expeditorType.createdAt = expeditorType.createdAt ? moment(expeditorType.createdAt) : undefined;
        expeditorType.updatedAt = expeditorType.updatedAt ? moment(expeditorType.updatedAt) : undefined;
      });
    }
    return res;
  }
}

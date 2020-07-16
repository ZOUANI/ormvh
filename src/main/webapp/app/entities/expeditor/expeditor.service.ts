import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExpeditor } from 'app/shared/model/expeditor.model';

type EntityResponseType = HttpResponse<IExpeditor>;
type EntityArrayResponseType = HttpResponse<IExpeditor[]>;

@Injectable({ providedIn: 'root' })
export class ExpeditorService {
  public resourceUrl = SERVER_API_URL + 'api/expeditors';

  constructor(protected http: HttpClient) {}

  create(expeditor: IExpeditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditor);
    return this.http
      .post<IExpeditor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expeditor: IExpeditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditor);
    return this.http
      .put<IExpeditor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpeditor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpeditor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expeditor: IExpeditor): IExpeditor {
    const copy: IExpeditor = Object.assign({}, expeditor, {
      createdAt: expeditor.createdAt && expeditor.createdAt.isValid() ? expeditor.createdAt.toJSON() : undefined,
      updatedAt: expeditor.updatedAt && expeditor.updatedAt.isValid() ? expeditor.updatedAt.toJSON() : undefined,
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
      res.body.forEach((expeditor: IExpeditor) => {
        expeditor.createdAt = expeditor.createdAt ? moment(expeditor.createdAt) : undefined;
        expeditor.updatedAt = expeditor.updatedAt ? moment(expeditor.updatedAt) : undefined;
      });
    }
    return res;
  }
}

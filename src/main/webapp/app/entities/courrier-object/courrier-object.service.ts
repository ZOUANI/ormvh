import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourrierObject } from 'app/shared/model/courrier-object.model';

type EntityResponseType = HttpResponse<ICourrierObject>;
type EntityArrayResponseType = HttpResponse<ICourrierObject[]>;

@Injectable({ providedIn: 'root' })
export class CourrierObjectService {
  public resourceUrl = SERVER_API_URL + 'api/courrier-objects';

  constructor(protected http: HttpClient) {}

  create(courrierObject: ICourrierObject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courrierObject);
    return this.http
      .post<ICourrierObject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(courrierObject: ICourrierObject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courrierObject);
    return this.http
      .put<ICourrierObject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICourrierObject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICourrierObject[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(courrierObject: ICourrierObject): ICourrierObject {
    const copy: ICourrierObject = Object.assign({}, courrierObject, {
      createdAt: courrierObject.createdAt != null && courrierObject.createdAt.isValid() ? courrierObject.createdAt.toJSON() : null,
      updatedAt: courrierObject.updatedAt != null && courrierObject.updatedAt.isValid() ? courrierObject.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((courrierObject: ICourrierObject) => {
        courrierObject.createdAt = courrierObject.createdAt != null ? moment(courrierObject.createdAt) : null;
        courrierObject.updatedAt = courrierObject.updatedAt != null ? moment(courrierObject.updatedAt) : null;
      });
    }
    return res;
  }
}

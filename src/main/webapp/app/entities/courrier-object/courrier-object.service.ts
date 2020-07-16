import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
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

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(courrierObject: ICourrierObject): ICourrierObject {
    const copy: ICourrierObject = Object.assign({}, courrierObject, {
      createdAt: courrierObject.createdAt && courrierObject.createdAt.isValid() ? courrierObject.createdAt.toJSON() : undefined,
      updatedAt: courrierObject.updatedAt && courrierObject.updatedAt.isValid() ? courrierObject.updatedAt.toJSON() : undefined,
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
      res.body.forEach((courrierObject: ICourrierObject) => {
        courrierObject.createdAt = courrierObject.createdAt ? moment(courrierObject.createdAt) : undefined;
        courrierObject.updatedAt = courrierObject.updatedAt ? moment(courrierObject.updatedAt) : undefined;
      });
    }
    return res;
  }
}

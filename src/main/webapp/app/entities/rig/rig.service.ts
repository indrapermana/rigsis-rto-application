import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRig } from 'app/shared/model/rig.model';

type EntityResponseType = HttpResponse<IRig>;
type EntityArrayResponseType = HttpResponse<IRig[]>;

@Injectable({ providedIn: 'root' })
export class RigService {
    public resourceUrl = SERVER_API_URL + 'api/rigs';

    constructor(private http: HttpClient) {}

    create(rig: IRig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rig);
        return this.http
            .post<IRig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rig: IRig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rig);
        return this.http
            .put<IRig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(rig: IRig): IRig {
        const copy: IRig = Object.assign({}, rig, {
            startDate: rig.startDate != null && rig.startDate.isValid() ? rig.startDate.toJSON() : null,
            endDate: rig.endDate != null && rig.endDate.isValid() ? rig.endDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((rig: IRig) => {
            rig.startDate = rig.startDate != null ? moment(rig.startDate) : null;
            rig.endDate = rig.endDate != null ? moment(rig.endDate) : null;
        });
        return res;
    }
}

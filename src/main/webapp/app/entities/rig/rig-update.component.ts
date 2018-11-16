import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IRig } from 'app/shared/model/rig.model';
import { RigService } from './rig.service';
import { IWellbore } from 'app/shared/model/wellbore.model';
import { WellboreService } from 'app/entities/wellbore';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job';

@Component({
    selector: 'jhi-rig-update',
    templateUrl: './rig-update.component.html'
})
export class RigUpdateComponent implements OnInit {
    rig: IRig;
    isSaving: boolean;

    wellbores: IWellbore[];

    jobs: IJob[];
    startDate: string;
    endDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private rigService: RigService,
        private wellboreService: WellboreService,
        private jobService: JobService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rig }) => {
            this.rig = rig;
            this.startDate = this.rig.startDate != null ? this.rig.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.rig.endDate != null ? this.rig.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.wellboreService.query().subscribe(
            (res: HttpResponse<IWellbore[]>) => {
                this.wellbores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.jobService.query().subscribe(
            (res: HttpResponse<IJob[]>) => {
                this.jobs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.rig.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.rig.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.rig.id !== undefined) {
            this.subscribeToSaveResponse(this.rigService.update(this.rig));
        } else {
            this.subscribeToSaveResponse(this.rigService.create(this.rig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRig>>) {
        result.subscribe((res: HttpResponse<IRig>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackWellboreById(index: number, item: IWellbore) {
        return item.id;
    }

    trackJobById(index: number, item: IJob) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

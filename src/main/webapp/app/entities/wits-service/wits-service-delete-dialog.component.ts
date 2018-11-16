import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWitsService } from 'app/shared/model/wits-service.model';
import { WitsServiceService } from './wits-service.service';

@Component({
    selector: 'jhi-wits-service-delete-dialog',
    templateUrl: './wits-service-delete-dialog.component.html'
})
export class WitsServiceDeleteDialogComponent {
    witsService: IWitsService;

    constructor(
        private witsServiceService: WitsServiceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.witsServiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'witsServiceListModification',
                content: 'Deleted an witsService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wits-service-delete-popup',
    template: ''
})
export class WitsServiceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ witsService }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WitsServiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.witsService = witsService;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

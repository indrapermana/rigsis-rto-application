/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RtoAppTestModule } from '../../../test.module';
import { WitsServiceDeleteDialogComponent } from 'app/entities/wits-service/wits-service-delete-dialog.component';
import { WitsServiceService } from 'app/entities/wits-service/wits-service.service';

describe('Component Tests', () => {
    describe('WitsService Management Delete Component', () => {
        let comp: WitsServiceDeleteDialogComponent;
        let fixture: ComponentFixture<WitsServiceDeleteDialogComponent>;
        let service: WitsServiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WitsServiceDeleteDialogComponent]
            })
                .overrideTemplate(WitsServiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WitsServiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WitsServiceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

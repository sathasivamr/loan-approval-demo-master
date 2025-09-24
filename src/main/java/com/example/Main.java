package com.example;

import com.example.model.loanApplication;
import com.example.kogito.KogitoBpmnManager;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Loan Approval System with BPMN Process Integration
 * 
 * This system demonstrates:
 * - 3 DRL rule files (basic-approval, risk-assessment, manual-review)
 * - BPMN process orchestration via KogitoBpmnManager
 * - Multi-stage business rule execution
 * - kmodule.xml configuration for knowledge bases
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Loan Approval System with BPMN Process ===\n");
        
        // Execute loan approval tests with BPMN process integration
        testAllScenariosWithBpmn();
    }
    
    /**
     * Test loan applications using BPMN process orchestration
     * Uses KogitoBpmnManager to execute multi-stage business rules
     */
    private static void testAllScenariosWithBpmn() {
        // Initialize BPMN manager with kmodule configuration
        KogitoBpmnManager bpmnManager = new KogitoBpmnManager();
        
        // Test various loan application scenarios
        testLoanApplicationWithBpmn("Alice", 25, 15000, 600, bpmnManager);    // Low income
        testLoanApplicationWithBpmn("Bob", 35, 60000, 750, bpmnManager);     // High income
        testLoanApplicationWithBpmn("Charlie", 28, 35000, 675, bpmnManager); // Medium income
        testLoanApplicationWithBpmn("Diana", 23, 30000, 650, bpmnManager);   // Young applicant
        testLoanApplicationWithBpmn("Eve", 30, 25000, 580, bpmnManager);     // Edge case
        testLoanApplicationWithBpmn("Frank", 40, 80000, 800, bpmnManager);   // Perfect case
        
        // Clean up resources
        bpmnManager.dispose();
    }
    
    /**
     * Test individual loan application with BPMN process
     * Executes business rules through BPMN process orchestration
     */
    private static void testLoanApplicationWithBpmn(String name, int age, int income, int creditScore, KogitoBpmnManager bpmnManager) {
        System.out.println("--- " + name + " ---");
        
        // Create loan application
        loanApplication app = new loanApplication(name, age, income, creditScore);
        System.out.println("Applicant: " + name + " (Age: " + age + ", Income: $" + income + ", Credit: " + creditScore + ")");

        try {
            // Execute BPMN process with business rules
            KogitoBpmnManager.ProcessExecutionResult result = bpmnManager.executeLoanApprovalProcess(app);
            
            if (result.isSuccess()) {
                System.out.println("Decision: " + result.getFinalStatus());
                System.out.println("Process: " + result.getProcessState() + " | Rules fired: " + result.getRulesFired());
                explainDecision(app);
            } else {
                System.err.println("Process failed: " + result.getErrorMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Explain the loan decision based on the application status
     */
    private static void explainDecision(loanApplication app) {
        String status = app.getStatus();
        String explanation;
        
        if (status == null) {
            explanation = "❓ No decision made";
        } else {
            switch (status) {
                case "APPROVED":
                    explanation = "✓ Approved: High income + good credit";
                    break;
                case "REJECTED":
                    explanation = "✗ Rejected: Low income or poor credit";
                    break;
                case "APPROVED_HIGH_RISK":
                    explanation = "⚠ High Risk Approval: Medium income + credit";
                    break;
                case "YOUNG_APPLICANT_REVIEW":
                    explanation = "👤 Young Applicant Review: Under 25";
                    break;
                case "REVIEW":
                    explanation = "📋 Manual Review: Requires assessment";
                    break;
                default:
                    explanation = "❓ Unknown status: " + status;
            }
        }
        
        System.out.println("Reason: " + explanation);
    }
}
